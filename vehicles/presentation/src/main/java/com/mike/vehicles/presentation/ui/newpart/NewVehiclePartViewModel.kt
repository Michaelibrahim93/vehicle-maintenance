package com.mike.vehicles.presentation.ui.newpart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.core.domain.utils.IoDispatcher
import com.mike.core.domain.utils.validator.Validator
import com.mike.core.domain.utils.validator.rules.NotBlankRule
import com.mike.core.domain.utils.validator.rules.NotNullRule
import com.mike.core.domain.utils.validator.rules.PastDateRule
import com.mike.core.domain.utils.validator.rules.ValidIntRule
import com.mike.core.domain.utils.validator.rules.ValidKmInputRule
import com.mike.core.presentation.navigation.Route
import com.mike.core.presentation.viewmodels.InitLoaderImpl
import com.mike.core.presentation.viewmodels.InitialLoader
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.repos.VehiclePartsRepository
import com.mike.domian.vehicles.repos.VehiclesRepository
import com.mike.domian.vehicles.usecases.AddVehiclePartUseCase
import com.mike.domian.vehicles.usecases.UpdateVehicleUseCase
import com.mike.resources.R
import com.mike.vehicles.presentation.ui.newpart.events.NewVehiclePartUiAction
import com.mike.vehicles.presentation.ui.newpart.uistate.NewVehiclePartUiState
import com.mike.vehicles.presentation.ui.newpart.uistate.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class NewVehiclePartViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val addVehiclePartUseCase: AddVehiclePartUseCase,
    private val vehiclePartsRepository: VehiclePartsRepository,
    private val vehiclesRepository: VehiclesRepository,
    private val updateVehicleUseCase: UpdateVehicleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), InitialLoader by InitLoaderImpl() {
    private val route: Route.NewVehiclePart = savedStateHandle.toRoute()

    private val _state = MutableStateFlow(NewVehiclePartUiState.initialState(
        vehicleId = route.vehicleId,
        partToBeRenewed = null // can be changed in intialization if partId is provided
    ))
    val state: StateFlow<NewVehiclePartUiState> = _state.onStart {
        Timber.d("state: started")
        loadInitValues()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = NewVehiclePartUiState.initialState(
            vehicleId = route.vehicleId,
            partToBeRenewed = null // can be changed in intialization if partId is provided
        )
    )

    private lateinit var vehicle: Vehicle

    private val _actionChannel = Channel<NewVehiclePartUiAction>()
    val actionFlow: Flow<NewVehiclePartUiAction> = _actionChannel.receiveAsFlow()

    private val validator: Validator = buildNewVehiclePartValidator()

    private fun loadInitValues() = loadInitValues {
        updatePrefilledFields(vehicleId = route.vehicleId, partId = route.vehiclePartId)
    }

    private fun updatePrefilledFields(
        vehicleId: Long,
        partId: Long?
    ) = viewModelScope.launch {
        try {
            val vehicle = withContext(dispatcher) {
                vehiclesRepository.loadVehicle(vehicleId)
            }
            val part = partId?.let{
                withContext(dispatcher) {
                    vehiclePartsRepository.loadVehiclePartById(partId)
                }
            }

            this@NewVehiclePartViewModel.vehicle = vehicle

            _state.value = NewVehiclePartUiState.initialState(
                vehicleId = route.vehicleId,
                vehicle= vehicle,
                partToBeRenewed = part // can be changed in intialization if partId is provided
            )
        } catch (t: Throwable) {
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
    }

    fun updateVehicleKm() = viewModelScope.launch {
        val uiState = this@NewVehiclePartViewModel._state.value
        val vehicle = this@NewVehiclePartViewModel.vehicle
        val updatedKm = uiState.deploymentKm.input.toDouble()
        val vehiclePart = uiState.toVehiclePart()

        _state.value = uiState.updateLoading(true)
        try {
            delay(1500)
            withContext(dispatcher) {
                updateVehicleUseCase.executeUpdateKm(
                    vehicleId = vehicle.id!!,
                    newKMs = updatedKm
                )
            }
            this@NewVehiclePartViewModel.vehicle = vehicle.copy(currentKM = updatedKm)

            withContext(dispatcher) {
                addVehiclePartUseCase.execute(vehiclePart)
            }
            _actionChannel.send(NewVehiclePartUiAction.ShowSuccess)
        } catch (t: Throwable) {
            Timber.w(t)
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
        _state.value = uiState.updateLoading(true)
    }

    private fun buildNewVehiclePartValidator(): Validator {
        return Validator.Builder()
            .addRule(NewVehiclePartUiState.KEY_PART_NAME, NotBlankRule(), R.string.required_field)
            .addRule(NewVehiclePartUiState.KEY_DEPLOYMENT_DATE, NotNullRule(), R.string.required_field)
            .addRule(NewVehiclePartUiState.KEY_DEPLOYMENT_DATE, PastDateRule(), R.string.invalid_input)
            .addRule(NewVehiclePartUiState.KEY_DEPLOYMENT_KM, NotBlankRule(), R.string.required_field)
            .addRule(NewVehiclePartUiState.KEY_DEPLOYMENT_KM, ValidKmInputRule(), R.string.invalid_input)
            .addRule(NewVehiclePartUiState.KEY_LIFE_SPAN_IN_MONTHS, NotBlankRule(), R.string.required_field)
            .addRule(NewVehiclePartUiState.KEY_LIFE_SPAN_IN_MONTHS, ValidIntRule(1), R.string.invalid_input)
            .addRule(NewVehiclePartUiState.KEY_LIFE_SPAN_IN_KM, NotBlankRule(), R.string.required_field)
            .addRule(NewVehiclePartUiState.KEY_LIFE_SPAN_IN_KM, ValidKmInputRule(), R.string.invalid_input)
            .build()
    }

    fun validateAndSave() {
        val uiState = _state.value
        val validationResult = validator.validate(uiState.toValidationMap())
        val vehicleKM = this.vehicle.currentKM
        val deploymentKmChange = uiState.deploymentKm.input.toDoubleOrNull() ?: 0.0

        _state.value = uiState.updateValidationErrors(validationResult)

        if (validationResult.isEmpty()) {
            if (vehicleKM < deploymentKmChange)
                viewModelScope.launch {
                    _actionChannel.send(NewVehiclePartUiAction.ValidationCarKm(
                        vehicleKm = vehicleKM,
                        partKm = deploymentKmChange
                    ))
                }
            else
                addVehiclePart(uiState.toVehiclePart())
        }
    }

    private fun addVehiclePart(
        vehiclePart: VehiclePart
    ) = viewModelScope.launch {
        val uiState = _state.value
        _state.value = uiState.copy(isLoading = true)
        try {
            delay(1500)
            withContext(dispatcher) {
                addVehiclePartUseCase.execute(vehiclePart)
            }
            _actionChannel.send(NewVehiclePartUiAction.ShowSuccess)
        } catch (t: Throwable) {
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
        _state.value = uiState.copy(isLoading = false)
    }
    //setters
    fun updatePartName(partName: String) {
        _state.value = _state.value.updatePartName(partName)
    }

    fun updateDeploymentDate(deploymentDate: Date) {
        _state.value = _state.value.updateDeploymentDate(deploymentDate)
    }

    fun updateDeploymentKm(deploymentKm: String) {
        _state.value = _state.value.updateDeploymentKm(km = deploymentKm)
    }

    fun updateLifeSpanInMonths(lifeSpanInMonths: String) {
        _state.value = _state.value.updateLifeSpanInMonths(months = lifeSpanInMonths)
    }

    fun updateLifeSpanInKm(lifeSpanInKm: String) {
        _state.value = _state.value.updateLifeSpanInKm(km = lifeSpanInKm)
    }

    fun updateSupplier(supplier: String) {
        _state.value = _state.value.updateSupplier(supplier = supplier)
    }

    fun updatePrice(price: String) {
        _state.value = _state.value.updatePrice(price = price)
    }
}
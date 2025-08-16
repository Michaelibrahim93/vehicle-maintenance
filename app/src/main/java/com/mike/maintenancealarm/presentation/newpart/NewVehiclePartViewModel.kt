package com.mike.maintenancealarm.presentation.newpart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.domain.usecases.AddVehiclePartUseCase
import com.mike.maintenancealarm.presentation.main.Route
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartEvent
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartUiAction
import com.mike.maintenancealarm.presentation.newpart.uistate.*
import com.mike.maintenancealarm.utils.IoDispatcher
import com.mike.maintenancealarm.utils.validator.Validator
import com.mike.maintenancealarm.utils.validator.rules.NotBlankRule
import com.mike.maintenancealarm.utils.validator.rules.NotNullRule
import com.mike.maintenancealarm.utils.validator.rules.PastDateRule
import com.mike.maintenancealarm.utils.validator.rules.ValidKmInputRule
import com.mike.maintenancealarm.utils.validator.rules.ValidIntRule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewVehiclePartViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val addVehiclePartUseCase: AddVehiclePartUseCase,
    private val vehiclePartsRepository: VehiclePartsRepository,
    vehiclesRepository: VehiclesRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route: Route.NewVehiclePart = savedStateHandle.toRoute()

    private val _state = MutableStateFlow(NewVehiclePartUiState.initialState(
        vehicleId = route.vehicleId,
        partToBeRenewed = null // can be changed in intialization if partId is provided
    ))
    val state: StateFlow<NewVehiclePartUiState> = _state

    private val sfVehicle: StateFlow<Vehicle?> = vehiclesRepository.listenToVehicleById(route.vehicleId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    private val _actionChannel = Channel<NewVehiclePartUiAction>()
    val actionFlow: Flow<NewVehiclePartUiAction> = _actionChannel.receiveAsFlow()

    private val validator: Validator = buildNewVehiclePartValidator()

    init {
        listenToVehicle()
        if (route.vehiclePartId != null)
            updatePrefilledFields(route.vehiclePartId)
    }

    private fun listenToVehicle() = viewModelScope.launch {
        sfVehicle.collectLatest { vehicle ->
            Timber.d("Vehicle: $vehicle")
        }
    }

    private fun updatePrefilledFields(partId: Long) = viewModelScope.launch {
        try {
            val part = withContext(dispatcher) {
                vehiclePartsRepository.loadVehiclePartById(partId)
            }
            _state.value = NewVehiclePartUiState.initialState(
                vehicleId = route.vehicleId,
                partToBeRenewed = part // can be changed in intialization if partId is provided
            )
        } catch (t: Throwable) {
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
    }

    fun onEvent(event: NewVehiclePartEvent) {
        when (event) {
            is NewVehiclePartEvent.OnPartNameChange ->
                _state.value = _state.value.updatePartName(event.partName)
            is NewVehiclePartEvent.OnDeploymentDateChange ->
                _state.value = _state.value.updateDeploymentDate(event.deploymentDate)
            is NewVehiclePartEvent.OnDeploymentKmChange ->
                _state.value = _state.value.updateDeploymentKm(km = event.deploymentKm)
            is NewVehiclePartEvent.OnLifeSpanInMonthsChange ->
                _state.value = _state.value.updateLifeSpanInMonths(months = event.lifeSpanInMonths)
            is NewVehiclePartEvent.OnLifeSpanInKmChange ->
                _state.value = _state.value.updateLifeSpanInKm(km = event.lifeSpanInKm)
            is NewVehiclePartEvent.OnSupplierChange ->
                _state.value = _state.value.updateSupplier(supplier = event.supplier)
            is NewVehiclePartEvent.OnPriceChange ->
                _state.value = _state.value.updatePrice(price = event.price)
            is NewVehiclePartEvent.OnSaveClick ->
                validateAndSave()
            else -> {}
        }
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

    private fun validateAndSave() {
        val uiState = _state.value
        val validationResult = validator.validate(uiState.toValidationMap())

        _state.value = uiState.updateValidationErrors(validationResult)

        if (validationResult.isEmpty())
            addVehiclePart(uiState.toVehiclePart())
    }

    private fun addVehiclePart(
        vehiclePart: VehiclePart
    ) = viewModelScope.launch {
        val uiState = _state.value
        _state.value = uiState.copy(isLoading = true)
        try {
            withContext(dispatcher) {
                addVehiclePartUseCase.execute(vehiclePart)
            }
            _actionChannel.send(NewVehiclePartUiAction.ShowSuccess)
        } catch (t: Throwable) {
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
        _state.value = uiState.copy(isLoading = false)
    }
}
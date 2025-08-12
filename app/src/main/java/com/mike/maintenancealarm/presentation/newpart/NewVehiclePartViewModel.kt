package com.mike.maintenancealarm.presentation.newpart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.domain.usecases.AddVehiclePartUseCase
import com.mike.maintenancealarm.presentation.main.Route
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartEvent
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartUiAction
import com.mike.maintenancealarm.presentation.newpart.uistate.*
import com.mike.maintenancealarm.utils.IoDispatcher
import com.mike.maintenancealarm.utils.validator.Validator
import com.mike.maintenancealarm.utils.validator.rules.NotBlankRule
import com.mike.maintenancealarm.utils.validator.rules.PastDateRule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewVehiclePartViewModel @Inject constructor(
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val addVehiclePartUseCase: AddVehiclePartUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val routeNewVehiclePart: Route.NewVehiclePart = savedStateHandle.toRoute()

    private val _state = MutableStateFlow(NewVehiclePartUiState(
        vehicleId = routeNewVehiclePart.vehicleId,
        partToBeRenewed = routeNewVehiclePart.vehiclePart
    ))
    val state: StateFlow<NewVehiclePartUiState> = _state

    private val _actionChannel = Channel<NewVehiclePartUiAction>()
    val actionFlow: Flow<NewVehiclePartUiAction> = _actionChannel.receiveAsFlow()

    private val validator: Validator = buildNewVehiclePartValidator()

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
            .addRule(NewVehiclePartUiState.KEY_DEPLOYMENT_DATE, PastDateRule(), R.string.required_field)
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
        try {
            withContext(dispatcher) {
                addVehiclePartUseCase.execute(vehiclePart)
            }
        } catch (t: Throwable) {
            _actionChannel.send(NewVehiclePartUiAction.ShowError(t))
        }
    }
}
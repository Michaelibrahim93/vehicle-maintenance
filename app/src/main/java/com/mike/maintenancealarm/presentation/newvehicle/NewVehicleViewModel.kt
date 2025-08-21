package com.mike.maintenancealarm.presentation.newvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.domain.usecases.AddVehicleUseCase
import com.mike.maintenancealarm.utils.IoDispatcher
import com.mike.maintenancealarm.utils.validator.Validator
import com.mike.maintenancealarm.utils.validator.rules.NotBlankRule
import com.mike.maintenancealarm.utils.validator.rules.ValidKmInputRule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewVehicleViewModel @Inject constructor(
    private val addVehicleUseCase: AddVehicleUseCase,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(NewVehicleState())
    val state: StateFlow<NewVehicleState> = _state

    private val _actionChannel = Channel<NewVehicleUiAction>()
    val actionFlow: Flow<NewVehicleUiAction> = _actionChannel.receiveAsFlow()

    private val validator = buildNewVehicleValidator()

    fun onEvent(event: NewVehicleEvent) {
        when (event) {
            is NewVehicleEvent.OnVehicleImageChange ->
                _state.value = _state.value.setVehicleImage(event.image)
            is NewVehicleEvent.OnVehicleNameChange ->
                _state.value = _state.value.setVehicleName(event.name)
            is NewVehicleEvent.OnVehicleKmChange ->
                _state.value = _state.value.setCurrentKM(event.km)
            is NewVehicleEvent.OnSaveClick -> validateAndSave()
            else -> {}
        }
    }

    private fun validateAndSave(
        state: NewVehicleState = _state.value
    ) = viewModelScope.launch {
        val validationResult = validator.validate(state.toValidationMap())
        _state.value = state.updateValidationErrors(validationResult)
        if (validationResult.isEmpty()) {
            _state.value = state.setLoading(true)
            try {
                delay(2000)
                withContext(dispatcher) {
                    addVehicleUseCase.execute(state.toVehicle())
                }
                _actionChannel.send(NewVehicleUiAction.ShowSuccess)
            } catch (t: Throwable) {
                _actionChannel.send(NewVehicleUiAction.ShowError(t))
                return@launch
            }
            _state.value = state.setLoading(false)
        }
    }

    private fun buildNewVehicleValidator(): Validator {
        return Validator.Builder()
            .addRule(NewVehicleState.KEY_VEHICLE_NAME, NotBlankRule(), R.string.required_field)
            .addRule(NewVehicleState.KEY_CURRENT_KM, NotBlankRule(), R.string.required_field)
            .addRule(NewVehicleState.KEY_CURRENT_KM, ValidKmInputRule(), R.string.invalid_input)
            .build()
    }
}
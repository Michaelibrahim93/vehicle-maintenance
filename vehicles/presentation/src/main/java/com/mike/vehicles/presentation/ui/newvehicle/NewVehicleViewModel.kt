package com.mike.vehicles.presentation.ui.newvehicle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.perf.performance
import com.mike.core.domain.utils.IoDispatcher
import com.mike.core.domain.utils.validator.Validator
import com.mike.core.domain.utils.validator.rules.NotBlankRule
import com.mike.core.domain.utils.validator.rules.ValidKmInputRule
import com.mike.domian.vehicles.usecases.AddVehicleUseCase
import com.mike.resources.R
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


    fun validateAndSave(
        state: NewVehicleState = _state.value
    ) = viewModelScope.launch {
        val trace = Firebase.performance.newTrace("add_vehicle")
        trace.start()
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
                trace.putAttribute("is_success", "true")
            } catch (t: Throwable) {
                _actionChannel.send(NewVehicleUiAction.ShowError(t))
                trace.putAttribute("is_success", "false")
            }
            _state.value = state.setLoading(false)
        }
        trace.stop()
    }

    private fun buildNewVehicleValidator(): Validator {
        return Validator.Builder()
            .addRule(NewVehicleState.KEY_VEHICLE_NAME, NotBlankRule(), R.string.required_field)
            .addRule(NewVehicleState.KEY_CURRENT_KM, NotBlankRule(), R.string.required_field)
            .addRule(NewVehicleState.KEY_CURRENT_KM, ValidKmInputRule(), R.string.invalid_input)
            .build()
    }

    //setters
    fun updateVehicleImage(image: String) {
        _state.value = _state.value.setVehicleImage(image)
    }

    fun updateVehicleName(name: String) {
        _state.value = _state.value.setVehicleName(name)
    }

    fun updateVehicleKm(km: String) {
        _state.value = _state.value.setCurrentKM(km)
    }
}
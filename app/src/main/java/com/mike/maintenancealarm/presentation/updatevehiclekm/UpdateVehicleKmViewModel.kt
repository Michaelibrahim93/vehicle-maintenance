package com.mike.maintenancealarm.presentation.updatevehiclekm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.domain.usecases.UpdateVehicleUseCase
import com.mike.maintenancealarm.utils.IoDispatcher
import com.mike.maintenancealarm.utils.validator.Validator
import com.mike.maintenancealarm.utils.validator.rules.NotBlankRule
import com.mike.maintenancealarm.utils.validator.rules.ValidKmInputRule
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class UpdateVehicleKmViewModel @AssistedInject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val updateVehicleUseCase: UpdateVehicleUseCase,
    private val vehicleRepository: VehiclesRepository,
    @Assisted private val vehicleId: Long
) : ViewModel() {
    private val _uiState = MutableStateFlow(UpdateVehicleKmUiState())
    val uiState = _uiState.onStart {
        loadVehicle()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UpdateVehicleKmUiState()
    )

    private val actionsChannel = Channel<UpdateVehicleKmUiAction>()
    val actionFlow: Flow<UpdateVehicleKmUiAction> = actionsChannel.receiveAsFlow()

    private val validator = buildNewVehicleValidator()

    fun onEvent(event: UpdateVehicleKmEvent) {
        Timber.d("Event: $event")
        when(event) {
            is UpdateVehicleKmEvent.OnKmChange ->
                _uiState.value = _uiState.value.setKm(event.km)

            is UpdateVehicleKmEvent.OnSaveClick -> validateAndSave()
            else -> {}
        }
    }

    private fun buildNewVehicleValidator() = Validator.Builder()
        .addRule(UpdateVehicleKmUiState.KEY_INPUT_KM, NotBlankRule(), R.string.required_field)
        .addRule(UpdateVehicleKmUiState.KEY_INPUT_KM, ValidKmInputRule(), R.string.invalid_input)
        .build()

    private fun loadVehicle() = viewModelScope.launch {
        val vehicle = withContext(ioDispatcher) {
            vehicleRepository.loadVehicle(vehicleId)
        }

        _uiState.value = _uiState.value.copy(
            vehicleName = vehicle.vehicleName,
            vehicleId = vehicle.id ?: 0
        )
    }

    private fun validateAndSave() = viewModelScope.launch {
        val validationResult = validator.validate(_uiState.value.toValidationMap())
        _uiState.value = _uiState.value.setValidationErrors(validationResult)
        if (!validationResult.isEmpty()) return@launch
        _uiState.value = _uiState.value.copy(isLoading = true)
        delay(2000)
        val vehicleId = _uiState.value.vehicleId
        val newKms = _uiState.value.inputKm.input.toDouble()
        try {
            withContext(ioDispatcher) {
                updateVehicleUseCase.executeUpdateKm(
                    vehicleId = vehicleId,
                    newKMs = newKms
                )
            }
            actionsChannel.send(UpdateVehicleKmUiAction.ShowSuccess)
        } catch (t: Throwable) {
            actionsChannel.send(UpdateVehicleKmUiAction.ShowError(t))
        }

        _uiState.value = _uiState.value.copy(isLoading = false)

    }

    @AssistedFactory
    interface Factory {
        fun create(vehicleId: Long): UpdateVehicleKmViewModel
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ViewModelEntryPoint {
    fun updateVehicleKmFactory(): UpdateVehicleKmViewModel.Factory
}
package com.mike.maintenancealarm.presentation.updatevehiclekm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.domain.usecases.UpdateVehicleUseCase
import com.mike.maintenancealarm.utils.IoDispatcher
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltViewModel
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

//    private val validator = buildNewVehicleValidator()



    fun onEvent(event: UpdateVehicleKmEvent) {
        Timber.d("Event: $event")
        when(event) {
            is UpdateVehicleKmEvent.OnKmChange ->
                _uiState.value = _uiState.value.setKm(event.km)

            is UpdateVehicleKmEvent.OnSaveClick -> validateAndSave()
            else -> {}
        }
    }

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

    }

    @AssistedFactory
    interface Factory {
        fun create(vehicleId: Long): UpdateVehicleKmViewModel
    }
}
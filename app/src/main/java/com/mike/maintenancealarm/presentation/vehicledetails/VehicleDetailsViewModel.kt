package com.mike.maintenancealarm.presentation.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.presentation.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    vehicleRepository: VehiclesRepository,
    partsRepository: VehiclePartsRepository,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    val vehicleId = savedStateHandle.toRoute<Route.VehicleDetails>().vehicleId
    private val vehicleFlow = vehicleRepository.listenToVehicleById(vehicleId)
    private val partsFlow = partsRepository.listenToVehicleParts(vehicleId)
    val fVehicleDetailsState: StateFlow<VehicleDetailsState> = combine(vehicleFlow, partsFlow) { vehicle, parts ->
        VehicleDetailsState(
            vehicle = vehicle,
            vehicleParts = parts
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VehicleDetailsState()
    )

    fun onEvent(event: VehicleDetailsEvents) {
        Timber.d("onEvent: $event")
    }
}
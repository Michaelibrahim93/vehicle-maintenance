package com.mike.maintenancealarm.presentaion.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.presentaion.main.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    vehicleRepository: VehiclesRepository,
    partsRepository: VehiclePartsRepository,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    val vehicleId = savedStateHandle.toRoute<Route.VehicleDetails>().vehicleId
    private val vehicleFlow = vehicleRepository.listenToVehicleById(vehicleId)
    private val parts = partsRepository.listenToVehicleParts(vehicleId)
    val fVehicleDetailsState: Flow<VehicleDetailsState> = combine(vehicleFlow, parts) { vehicle, parts ->
        VehicleDetailsState(
            vehicle = vehicle,
            vehicleParts = parts
        )
    }

    fun onEvent(events: VehicleDetailsEvents) {

    }
}
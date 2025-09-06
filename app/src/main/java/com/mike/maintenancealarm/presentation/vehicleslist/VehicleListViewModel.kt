package com.mike.maintenancealarm.presentation.vehicleslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.domian.vehicles.models.Vehicles
import com.mike.domian.vehicles.repos.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    vehiclesRepository: VehiclesRepository,
) : ViewModel() {
    private val sfVehicles: StateFlow<Vehicles> = vehiclesRepository.listenToAllVehicles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val state: Flow<VehicleListState> = sfVehicles.map { vehicles ->
        VehicleListState(vehicles = vehicles)
    }
}
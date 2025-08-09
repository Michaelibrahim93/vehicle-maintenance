package com.mike.maintenancealarm.presentaion.vehicleslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.data.vo.Vehicles
import com.mike.maintenancealarm.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

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
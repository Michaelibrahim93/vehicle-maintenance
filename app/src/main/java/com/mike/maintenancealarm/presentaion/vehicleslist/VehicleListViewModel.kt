package com.mike.maintenancealarm.presentaion.vehicleslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val vehiclesRepository: VehiclesRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    val vehicles: StateFlow<List<Vehicle>> = vehiclesRepository.listenToAllVehicles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertVehicle() = viewModelScope.launch {
        val vehicle = Vehicle(
            id = null,
            vehicleName = "Vehicle ${vehicles.value.size + 1}",
            currentKM = Random.nextInt(1000, 100000).toDouble(),
            lastKmUpdate = Date(),
            vehicleStatus = VehicleStatus.OK
        )
        withContext(ioDispatcher) {
            vehiclesRepository.insertVehicle(vehicle)
        }
    }

}
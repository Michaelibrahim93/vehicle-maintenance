package com.mike.domian.vehicles.repos

import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.Vehicles
import kotlinx.coroutines.flow.Flow

interface VehiclesRepository {
    fun listenToAllVehicles(): Flow<Vehicles>
    fun listenToVehicleById(id: Long): Flow<Vehicle?>
    suspend fun insertVehicle(vehicle: Vehicle)
    suspend fun updateVehicle(vehicle: Vehicle)
    suspend fun loadVehicle(id: Long): Vehicle

    suspend fun loadAllVehiclesIds(): List<Long>
}
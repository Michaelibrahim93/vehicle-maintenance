package com.mike.maintenancealarm.data.repo

import com.mike.maintenancealarm.data.storage.db.dao.VehicleDao
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.Vehicles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface VehiclesRepository {
    fun listenToAllVehicles(): Flow<Vehicles>
    fun listenToVehicleById(id: Long): Flow<Vehicle?>
    suspend fun insertVehicle(vehicle: Vehicle)
    suspend fun updateVehicle(vehicle: Vehicle)
    suspend fun loadVehicle(id: Long): Vehicle?
}

class VehiclesRepositoryImpl @Inject constructor(
    private val vehicleDao: VehicleDao
): VehiclesRepository {
    override fun listenToAllVehicles(): Flow<Vehicles> {
        return vehicleDao.listenToAllVehicles().map { list -> list.map { it.toVehicle() } }
    }

    override fun listenToVehicleById(id: Long): Flow<Vehicle?> {
        return vehicleDao.listenToVehicleById(id).map { it?.toVehicle() }
    }

    override suspend fun insertVehicle(vehicle: Vehicle) {
        vehicleDao.insertVehicle(vehicle.toEntity())
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        vehicleDao.updateVehicle(vehicle.toEntity())
    }

    override suspend fun loadVehicle(id: Long): Vehicle? {
        return vehicleDao.loadVehicleById(id)?.toVehicle()
    }
}
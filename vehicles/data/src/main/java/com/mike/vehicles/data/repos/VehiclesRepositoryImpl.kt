package com.mike.vehicles.data.repos

import android.database.sqlite.SQLiteConstraintException
import com.mike.core.domain.models.VehicleError
import com.mike.core.domain.models.VehicleErrorFactory
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.Vehicles
import com.mike.domian.vehicles.repos.VehiclesRepository
import com.mike.vehicles.data.storage.db.dao.VehicleDao
import com.mike.vehicles.data.storage.db.models.toEntity
import com.mike.vehicles.data.storage.db.models.toVehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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
        try {
            vehicleDao.insertVehicle(vehicle.toEntity())
        } catch (e: SQLiteConstraintException) {
            throw VehicleErrorFactory.vehicleNameExistsError(e)
        } catch (t: Throwable) {
            throw VehicleErrorFactory.unknownError(t)
        }
    }

    override suspend fun updateVehicle(vehicle: Vehicle) {
        try {
            vehicleDao.updateVehicle(vehicle.toEntity())
        } catch (t: Throwable) {
            throw VehicleErrorFactory.unknownError(t)
        }
    }

    override suspend fun loadVehicle(id: Long): Vehicle {
        try {
            return vehicleDao.loadVehicleById(id)?.toVehicle() ?:
            throw VehicleErrorFactory.vehicleNotFoundError(
                throwable = IllegalArgumentException("Vehicle with id $id not found")
            )
        } catch (t: VehicleError.LocalDbError) {
            throw t
        } catch (t: Throwable) {
            throw VehicleErrorFactory.unknownError(t)
        }
    }

    override suspend fun loadAllVehiclesIds(): List<Long> {
        return vehicleDao.loadAllVehiclesIds()
    }
}
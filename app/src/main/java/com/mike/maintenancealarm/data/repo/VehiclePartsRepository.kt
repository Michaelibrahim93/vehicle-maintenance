package com.mike.maintenancealarm.data.repo

import com.mike.maintenancealarm.data.storage.db.dao.VehiclePartDao
import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.data.vo.VehicleParts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface VehiclePartsRepository {
    fun listenToVehicleParts(vehicleId: Long): Flow<VehicleParts>
    suspend fun insertVehiclePart(vehiclePart: VehiclePart)
}

class VehiclePartsRepositoryImpl @Inject constructor(
    private val vehiclePartDao: VehiclePartDao
): VehiclePartsRepository {
    private val dayDateFormat = VehiclePartEntity.entityDateFormat()
    override fun listenToVehicleParts(vehicleId: Long): Flow<VehicleParts> {
        return vehiclePartDao.listenToVehicleParts(vehicleId).map {
            list -> list.map { it.toVehiclePart(dayDateFormat) }
        }
    }

    override suspend fun insertVehiclePart(vehiclePart: VehiclePart) {
        vehiclePartDao.insertVehiclePart(vehiclePart.toEntity(dayDateFormat))
    }
}
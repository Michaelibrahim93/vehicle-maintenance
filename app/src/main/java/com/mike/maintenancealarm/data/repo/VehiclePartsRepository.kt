package com.mike.maintenancealarm.data.repo

import com.mike.maintenancealarm.data.storage.db.dao.VehiclePartDao
import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.data.vo.VehicleParts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

interface VehiclePartsRepository {
    fun listenToVehicleParts(vehicleId: Long): Flow<VehicleParts>
    @Throws
    suspend fun insertVehiclePart(vehiclePart: VehiclePart)

}

class VehiclePartsRepositoryImpl @Inject constructor(
    private val vehiclePartDao: VehiclePartDao
): VehiclePartsRepository {
    private val dayDateFormat = VehiclePartEntity.entityDateFormat()

    override fun listenToVehicleParts(vehicleId: Long): Flow<VehicleParts> {
        return vehiclePartDao.listenToVehicleParts(vehicleId).map { list ->
            Timber.d("listenToVehicleParts: $list")
            list.map { it.toVehiclePart(dayDateFormat) }
        }
    }

    @Throws
    override suspend fun insertVehiclePart(vehiclePart: VehiclePart) {
        Timber.d("insertVehiclePart: $vehiclePart")
        vehiclePartDao.insertVehiclePart(vehiclePart.toEntity(dayDateFormat))
    }
}
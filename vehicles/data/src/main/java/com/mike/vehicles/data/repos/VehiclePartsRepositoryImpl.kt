package com.mike.vehicles.data.repos

import com.mike.core.domain.models.VehicleErrorFactory
import com.mike.vehicles.data.storage.db.dao.VehiclePartDao
import com.mike.vehicles.data.storage.db.models.VehiclePartEntity
import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.models.VehicleParts
import com.mike.domian.vehicles.repos.VehiclePartsRepository
import com.mike.vehicles.data.storage.db.models.toEntity
import com.mike.vehicles.data.storage.db.models.toVehiclePart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

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

    override suspend fun loadVehicleParts(vehicleId: Long): VehicleParts {
        return vehiclePartDao.loadVehicleParts(vehicleId)
            .map { it.toVehiclePart(dayDateFormat) }
    }

    override suspend fun loadVehiclePartById(vehicleId: Long): VehiclePart {
        return vehiclePartDao.loadVehiclePartById(vehicleId)
            ?.toVehiclePart(dayDateFormat)
            ?.also { Timber.d("loadVehiclePartById: $it") }
            ?: throw VehicleErrorFactory.vehicleNotFoundError(
                IllegalArgumentException("Vehicle part with id $vehicleId not found")
            )
    }

    @Throws
    override suspend fun insertVehiclePart(vehiclePart: VehiclePart) {
        Timber.d("insertVehiclePart: $vehiclePart")
        vehiclePartDao.insertVehiclePart(vehiclePart.toEntity(dayDateFormat))
    }

    override suspend fun updateVehiclePart(vehiclePart: VehiclePart) {
        Timber.d("insertVehiclePart: $vehiclePart")
        vehiclePartDao.updateVehiclePart(vehiclePart.toEntity(dayDateFormat))
    }
}
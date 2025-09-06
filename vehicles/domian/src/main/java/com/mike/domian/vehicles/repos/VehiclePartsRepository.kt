package com.mike.domian.vehicles.repos

import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.models.VehicleParts
import kotlinx.coroutines.flow.Flow

interface VehiclePartsRepository {
    fun listenToVehicleParts(vehicleId: Long): Flow<VehicleParts>

    suspend fun loadVehicleParts(vehicleId: Long): VehicleParts

    suspend fun loadVehiclePartById(vehicleId: Long): VehiclePart
    @Throws
    suspend fun insertVehiclePart(vehiclePart: VehiclePart)

    @Throws
    suspend fun updateVehiclePart(vehiclePart: VehiclePart)
}
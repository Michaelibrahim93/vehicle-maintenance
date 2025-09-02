package com.mike.maintenancealarm.domain.repos

import com.mike.maintenancealarm.domain.vo.VehiclePart
import com.mike.maintenancealarm.domain.vo.VehicleParts
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
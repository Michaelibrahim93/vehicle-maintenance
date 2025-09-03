package com.mike.maintenancealarm.modelFactory

import com.mike.maintenancealarm.domain.models.Vehicle
import com.mike.maintenancealarm.domain.models.VehicleStatus
import java.util.Date

object TestVehicleFactory {
    fun generateVehicle(
        id: Long = 0,
        vehicleName: String = "Test Vehicle",
        vehicleImage: String? = null,
        currentKM: Double = 1000.0,
        lastKmUpdate: Date = Date(),
        vehicleStatus: VehicleStatus = VehicleStatus.OK
    ) = Vehicle(
        id = id,
        vehicleName = vehicleName,
        vehicleImage = vehicleImage,
        currentKM = currentKM,
        lastKmUpdate = lastKmUpdate,
        vehicleStatus = vehicleStatus
    )
}
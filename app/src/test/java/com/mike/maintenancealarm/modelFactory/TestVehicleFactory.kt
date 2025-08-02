package com.mike.maintenancealarm.modelFactory

import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import java.util.Date

object TestVehicleFactory {
    fun generateVehicle() = Vehicle(
        id = 0,
        vehicleName = "Test Vehicle",
        vehicleImage = null,
        currentKM = 1000.0,
        lastKmUpdate = Date(),
        vehicleStatus = VehicleStatus.OK
    )
}
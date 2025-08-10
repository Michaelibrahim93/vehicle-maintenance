package com.mike.maintenancealarm.modelFactory

import com.mike.maintenancealarm.data.vo.LifeSpan
import com.mike.maintenancealarm.data.vo.VehiclePart
import java.util.Date

object TestVehiclePartFactory {
    fun generateVehiclePart(
        id: Long = 0L,
        vehicleId: Long = 1L,
        partName: String = "Test Part",
        deploymentDate: Date = Date(),
        deploymentKM: Double = 10000.0,
        lifeSpan: LifeSpan = LifeSpan(km = 5000.0, months = 6),
        supplier: String? = "Test Supplier",
        price: Double? = 100.0
    ): VehiclePart {
        return VehiclePart(
            id = id,
            vehicleId = vehicleId,
            partName = partName,
            deploymentDate = deploymentDate,
            deploymentKM = deploymentKM,
            lifeSpan = lifeSpan,
            supplier = supplier,
            price = price
        )
    }
}
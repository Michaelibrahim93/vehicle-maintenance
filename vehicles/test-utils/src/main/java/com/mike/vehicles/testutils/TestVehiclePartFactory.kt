package com.mike.vehicles.testutils

import com.mike.domian.vehicles.models.LifeSpan
import com.mike.domian.vehicles.models.VehiclePart
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

    fun generateVehicleParts(
        count: Int,
        deploymentKMList: List<Double>,
        vehicleId: Long = 1L,
        basePartName: String = "Test Part",
        deploymentDate: Date = Date(),
        lifeSpan: LifeSpan = LifeSpan(km = 5000.0, months = 6),
        supplier: String? = "Test Supplier",
        price: Double? = 100.0
    ): List<VehiclePart> {
        val vehicleParts = mutableListOf<VehiclePart>()
        for (i in 1..count) {
            val vehiclePart = generateVehiclePart(
                vehicleId = vehicleId,
                partName = "$basePartName $i",
                deploymentDate = deploymentDate,
                deploymentKM = deploymentKMList[i - 1],
                lifeSpan = lifeSpan,
                supplier = supplier,
                price = price
            )
            vehicleParts.add(vehiclePart)
        }
        return vehicleParts
    }
}
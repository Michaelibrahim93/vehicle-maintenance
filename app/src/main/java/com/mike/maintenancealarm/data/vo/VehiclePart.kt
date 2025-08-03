package com.mike.maintenancealarm.data.vo

import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity

typealias VehicleParts = List<VehiclePart>

data class VehiclePart(
    val id: Long = -1,
    val vehicleId: Long = -1,
    val partName: String,
    val deploymentDate: String,
    val deploymentKM: Double,
    val lifeSpan: LifeSpan,
    val supplier: String? = null,
    val price: Double? = null,
) {
    fun toEntity() = VehiclePartEntity(
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

data class LifeSpan(
    val km: Double,
    val months: Int
)
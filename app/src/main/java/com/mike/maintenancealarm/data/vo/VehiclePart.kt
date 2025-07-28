package com.mike.maintenancealarm.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity

@Entity(tableName = "vehicle_parts")
data class VehiclePart(
    @PrimaryKey(autoGenerate = true)
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
package com.mike.maintenancealarm.data.storage.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mike.maintenancealarm.data.vo.LifeSpan
import com.mike.maintenancealarm.data.vo.VehiclePart

@Entity(tableName = "vehicle_parts")
data class VehiclePartEntity(
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
    fun toVehiclePart() = VehiclePart(
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
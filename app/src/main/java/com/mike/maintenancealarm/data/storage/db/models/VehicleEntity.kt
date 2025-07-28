package com.mike.maintenancealarm.data.storage.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import java.util.Date

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
    val vehicleName: String,
    val currentKM: Double,
    val lastKmUpdate: Date,
    val vehicleStatus: VehicleStatus = VehicleStatus.OK
) {
    fun toVehicle() = Vehicle(
        id = id,
        vehicleName = vehicleName,
        currentKM = currentKM,
        lastKmUpdate = lastKmUpdate,
        vehicleStatus = vehicleStatus
    )
}

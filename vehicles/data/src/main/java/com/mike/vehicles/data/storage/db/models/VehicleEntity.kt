package com.mike.vehicles.data.storage.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.VehicleStatus
import java.util.Date

@Entity(
    tableName = "vehicles",
    indices = [Index(value = ["vehicleName"], unique = true)]
)
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,// 0 means new vehicle
    val vehicleImage: String?,
    val vehicleName: String,
    val currentKM: Double,
    val lastKmUpdate: Date,
    val vehicleStatus: VehicleStatus = VehicleStatus.OK
)

fun Vehicle.toEntity() = VehicleEntity(
    id = id ?: 0,
    vehicleName = vehicleName,
    vehicleImage = vehicleImage,
    currentKM = currentKM,
    lastKmUpdate = lastKmUpdate,
    vehicleStatus = vehicleStatus
)

fun VehicleEntity.toVehicle() = Vehicle(
    id = id,
    vehicleImage = vehicleImage,
    vehicleName = vehicleName,
    currentKM = currentKM,
    lastKmUpdate = lastKmUpdate,
    vehicleStatus = vehicleStatus
)


package com.mike.maintenancealarm.data.vo

import com.mike.maintenancealarm.data.storage.db.models.VehicleEntity
import java.util.Date

typealias Vehicles = List<Vehicle>

data class Vehicle(
    val id: Long?,
    val vehicleImage: String,
    val vehicleName: String,
    val currentKM: Double,
    val lastKmUpdate: Date,
    val vehicleStatus: VehicleStatus
) {
    fun toEntity() = VehicleEntity(
            id = id ?: -1,
            vehicleName = vehicleName,
            currentKM = currentKM,
            lastKmUpdate = lastKmUpdate,
            vehicleStatus = vehicleStatus
        )
}

enum class VehicleStatus(
    val value: Int
) {
    OK(value = 0),
    HAS_NEAR_EXPIRATION(value = 1),
    HAS_EXPIRED(value = 2)
}

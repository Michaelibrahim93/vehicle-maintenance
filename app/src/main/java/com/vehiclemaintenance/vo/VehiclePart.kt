package com.vehiclemaintenance.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehiclePart(
    @PrimaryKey(autoGenerate = true)
    val partId: Long,
    val vehicleId: Long,
    val partName: String,
    val startingDistance: Double,
    val expirationDistance: Double,
    val expirationTime: Int, //expiration month
    val replacementTime: Long
)

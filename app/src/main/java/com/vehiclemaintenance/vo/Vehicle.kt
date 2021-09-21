package com.vehiclemaintenance.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val model: String,
    val manufacturer: String,
    val modelYear: Int,
    val imageUri: String,
    val distance: Double
)

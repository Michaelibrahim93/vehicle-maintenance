package com.mike.vehicles.data.storage.db.typeconverters

import androidx.room.TypeConverter
import com.mike.domian.vehicles.models.LifeSpan
import com.mike.domian.vehicles.models.VehicleStatus
import java.util.Date

class VehicleConverters {
    // Date <-> Long (timestamp)
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    // VehicleStatus <-> String (or use ordinal Int if you prefer)
    @TypeConverter
    fun fromVehicleStatus(status: VehicleStatus): String = status.name

    @TypeConverter
    fun toVehicleStatus(status: String): VehicleStatus = VehicleStatus.valueOf(status)

    @TypeConverter
    fun fromLifeSpan(lifeSpan: LifeSpan): String {
        return "${lifeSpan.km},${lifeSpan.months}"
    }

    @TypeConverter
    fun toLifeSpan(lifeSpan: String): LifeSpan {
        val parts = lifeSpan.split(",")
        return LifeSpan(parts[0].toDouble(), parts[1].toInt())
    }
}
package com.mike.maintenancealarm.data.storage.db.typeconverters

import androidx.room.TypeConverter
import com.mike.maintenancealarm.data.vo.VehicleStatus
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
}
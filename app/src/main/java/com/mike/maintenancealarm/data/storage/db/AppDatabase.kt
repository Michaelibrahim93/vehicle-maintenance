package com.mike.maintenancealarm.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.maintenancealarm.data.storage.db.dao.VehicleDao
import com.mike.maintenancealarm.data.storage.db.dao.VehiclePartDao
import com.mike.maintenancealarm.data.storage.db.models.VehicleEntity
import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity
import com.mike.maintenancealarm.data.storage.db.typeconverters.VehicleConverters

@Database(
    entities = [VehicleEntity::class, VehiclePartEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(VehicleConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehiclePartDao(): VehiclePartDao
}
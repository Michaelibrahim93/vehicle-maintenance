package com.mike.vehicles.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mike.vehicles.data.storage.db.models.VehicleEntity
import com.mike.vehicles.data.storage.db.models.VehiclePartEntity
import com.mike.vehicles.data.storage.db.typeconverters.VehicleConverters
import com.mike.vehicles.data.storage.db.dao.VehicleDao
import com.mike.vehicles.data.storage.db.dao.VehiclePartDao

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
package com.vehiclemaintenance.dataaccess.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vehiclemaintenance.dataaccess.db.dao.VehicleDao
import com.vehiclemaintenance.dataaccess.db.dao.VehiclePartDao
import com.vehiclemaintenance.vo.Vehicle
import com.vehiclemaintenance.vo.VehiclePart

@Database(
    entities = [Vehicle::class, VehiclePart::class],
    version = 1,
    exportSchema = false
)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun vehiclePartDao(): VehiclePartDao
}
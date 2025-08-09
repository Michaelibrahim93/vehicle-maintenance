package com.mike.maintenancealarm.data.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiclePartDao {
    @Query("SELECT * FROM vehicle_parts WHERE vehicleId = :vehicleId")
    fun listenToVehicleParts(vehicleId: Long): Flow<List<VehiclePartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehiclePart(vehiclePart: VehiclePartEntity)
}
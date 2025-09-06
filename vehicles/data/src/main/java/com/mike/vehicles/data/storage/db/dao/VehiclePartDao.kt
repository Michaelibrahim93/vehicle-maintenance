package com.mike.vehicles.data.storage.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mike.vehicles.data.storage.db.models.VehiclePartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VehiclePartDao {
    @Query("SELECT * FROM vehicle_parts WHERE vehicleId = :vehicleId")
    fun listenToVehicleParts(vehicleId: Long): Flow<List<VehiclePartEntity>>

    @Query("SELECT * FROM vehicle_parts WHERE vehicleId = :vehicleId")
    suspend fun loadVehicleParts(vehicleId: Long): List<VehiclePartEntity>

    @Query("SELECT * FROM vehicle_parts WHERE id = :vehiclePartId LIMIT 1")
    suspend fun loadVehiclePartById(vehiclePartId: Long): VehiclePartEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertVehiclePart(vehiclePart: VehiclePartEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateVehiclePart(vehiclePart: VehiclePartEntity)
}
package com.mike.maintenancealarm.data.storage.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mike.maintenancealarm.data.vo.VehiclePartRecord
import java.util.Date

@Entity(tableName = "vehicle_part_records")
data class VehiclePartRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val historyPointId: Long = -1,
    val partId: Long = -1,
    val vehicleId: Long = -1,
    val deploymentDate: Date = Date(),
    val deploymentKM: Double = 0.0,
    val changeDate: Date? = null,
    val changeKM: Double? = null,
    val supplier: String? = null,
    val price: Double? = null,
) {
    fun toVehiclePartRecord() = VehiclePartRecord(
        historyPointId = this.historyPointId,
        partId = this.partId,
        vehicleId = this.vehicleId,
        deploymentDate = this.deploymentDate,
        deploymentKM = this.deploymentKM,
        changeDate = changeDate,
        changeKM = changeKM,
        supplier = supplier,
        price = price
    )
}

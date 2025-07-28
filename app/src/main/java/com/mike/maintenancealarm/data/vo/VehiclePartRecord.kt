package com.mike.maintenancealarm.data.vo

import com.mike.maintenancealarm.data.storage.db.models.VehiclePartRecordEntity
import java.util.Date

data class VehiclePartRecord(
    val historyPointId: Long = -1,
    val partId: Long = -1,
    val vehicleId: Long = -1,
    val deploymentDate: Date,
    val deploymentKM: Double,
    val changeDate: Date? = null,
    val changeKM: Double? = null,
    val supplier: String? = null,
    val price: Double? = null,
) {
    fun toEntity() = VehiclePartRecordEntity(
            historyPointId = historyPointId,
            partId = partId,
            vehicleId = vehicleId,
            deploymentDate = deploymentDate,
            deploymentKM = deploymentKM,
            changeDate = changeDate,
            changeKM = changeKM,
            supplier = supplier,
            price = price
        )

}

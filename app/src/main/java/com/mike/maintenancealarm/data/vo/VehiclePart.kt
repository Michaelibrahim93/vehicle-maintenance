package com.mike.maintenancealarm.data.vo

import com.mike.maintenancealarm.data.storage.db.models.VehiclePartEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

typealias VehicleParts = List<VehiclePart>

data class VehiclePart(
    val id: Long = -1,
    val vehicleId: Long = -1,
    val partName: String,
    val deploymentDate: Date,
    val deploymentKM: Double,
    val lifeSpan: LifeSpan,
    val supplier: String? = null,
    val price: Double? = null,
) {
    fun partStatus(
        currentVehicleKm: Double
    ): VehiclePartStatus {
        val currentCal = Calendar.getInstance()
        val deploymentCal = Calendar.getInstance().apply {
            time = deploymentDate
        }
        val expiryCal = Calendar.getInstance().apply {
            time = deploymentDate
            add(Calendar.MONTH, lifeSpan.months)
        }
        val kmLeft = lifeSpan.km - (currentVehicleKm - deploymentKM)

        val kmNearExpiry = kmLeft < (lifeSpan.km * 0.1)
        val dateNearExpiry = (expiryCal.timeInMillis - deploymentCal.timeInMillis) <
                lifeSpan.monthMillis() * 0.1

        return when {
            kmLeft <= 0 || currentCal.after(expiryCal) -> VehiclePartStatus.EXPIRED
            kmNearExpiry || dateNearExpiry -> VehiclePartStatus.NEAR_EXPIRATION
            else -> VehiclePartStatus.OK
        }
    }

    fun toEntity(
        dateFormat: SimpleDateFormat
    ) = VehiclePartEntity(
        id = id,
        vehicleId = vehicleId,
        partName = partName,
        deploymentDate = dateFormat.format(dateFormat),
        deploymentKM = deploymentKM,
        lifeSpan = lifeSpan,
        supplier = supplier,
        price = price
    )
}

data class LifeSpan(
    val km: Double,
    val months: Int
) {
    fun monthMillis(): Long {
        return (months * 30L * 24 * 60 * 60 * 1000)
    }
}

enum class VehiclePartStatus(
    val value: Int
) {
    OK(value = 0),
    NEAR_EXPIRATION(value = 1),
    EXPIRED(value = 2)
}
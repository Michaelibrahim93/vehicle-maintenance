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
    val expiryKm: Double
        get() = deploymentKM + lifeSpan.km

    val expiryDate: Date
        get() {
            val cal = Calendar.getInstance().apply {
                time = deploymentDate
                add(Calendar.MONTH, lifeSpan.months)
            }
            return cal.time
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
    EXPIRED(value = 2);

    companion object {
        fun partStatus(
            part: VehiclePart,
            currentVehicleKm: Double
        ): VehiclePartStatus {
            val currentCal = Calendar.getInstance()
            val deploymentCal = Calendar.getInstance().apply {
                time = part.deploymentDate
            }
            val expiryCal = Calendar.getInstance().apply {
                time = part.expiryDate
            }

            val kmLeft = part.lifeSpan.km - (currentVehicleKm - part.deploymentKM)

            val kmNearExpiry = kmLeft < (part.lifeSpan.km * 0.1)
            val dateNearExpiry = (expiryCal.timeInMillis - deploymentCal.timeInMillis) <
                    part.lifeSpan.monthMillis() * 0.1

            return when {
                kmLeft <= 0 || currentCal.after(expiryCal) -> EXPIRED
                kmNearExpiry || dateNearExpiry -> NEAR_EXPIRATION
                else -> OK
            }
        }
    }
}
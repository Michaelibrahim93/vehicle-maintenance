package com.mike.maintenancealarm.data.storage.db.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mike.maintenancealarm.domain.vo.LifeSpan
import com.mike.maintenancealarm.domain.vo.VehiclePart
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(
    tableName = "vehicle_parts",
    indices = [Index(value = ["partName"], unique = true)]
)
data class VehiclePartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val vehicleId: Long = 0,
    val partName: String,
    val deploymentDate: String,
    val deploymentKM: Double,
    val lifeSpan: LifeSpan,
    val supplier: String? = null,
    val price: Double? = null,
) {
    fun toVehiclePart(
        dateFormat: SimpleDateFormat
    ) = VehiclePart(
        id = id,
        vehicleId = vehicleId,
        partName = partName,
        deploymentDate = dateFormat.parse(deploymentDate) ?: Date(),
        deploymentKM = deploymentKM,
        lifeSpan = lifeSpan,
        supplier = supplier,
        price = price
    )

    companion object {
        private const val ENTITY_DATE_FORMAT = "yyyy-MM-dd"

        fun entityDateFormat(): SimpleDateFormat {
            return SimpleDateFormat(ENTITY_DATE_FORMAT, Locale.ENGLISH)
        }
    }
}
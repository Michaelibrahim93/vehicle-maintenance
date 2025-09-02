package com.mike.maintenancealarm.presentation.vehicledetails

import com.mike.maintenancealarm.domain.vo.Vehicle
import com.mike.maintenancealarm.domain.vo.VehiclePart
import com.mike.maintenancealarm.domain.vo.VehiclePartStatus
import com.mike.maintenancealarm.domain.vo.VehicleParts

data class VehicleDetailsState(
    val vehicleId: Long = 0,
    val vehicle: Vehicle? = null,
    val vehicleParts: VehicleParts? = null,
    val showUpdateKmDialog: Boolean = false
) {
    val displayList: List<DetailsItem>
        get() = if (vehicle == null || vehicleParts == null)
                emptyList()
            else if (vehicleParts.isEmpty())
                listOf<DetailsItem>(DetailsItem.VehicleItem(vehicle)) +
                        listOf(DetailsItem.NoAddedParts)
            else
                listOf<DetailsItem>(DetailsItem.VehicleItem(vehicle)) +
                        vehicleParts.map { DetailsItem.PartItem(
                            part = it,
                            partStatus = VehiclePartStatus.partStatus(
                                part = it,
                                currentVehicleKm = vehicle.currentKM
                            )
                        ) }
}

sealed class DetailsItem {
    data class VehicleItem(val vehicle: Vehicle) : DetailsItem()
    data class PartItem(
        val part: VehiclePart,
        val partStatus: VehiclePartStatus
    ) : DetailsItem()
    object NoAddedParts : DetailsItem()
}
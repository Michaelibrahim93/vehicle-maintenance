package com.mike.maintenancealarm.presentaion.vehicledetails

import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.data.vo.VehicleParts

data class VehicleDetailsState(
    val vehicle: Vehicle? = null,
    val vehicleParts: VehicleParts? = null
) {
    val displayList: List<DetailsItem>
        get() = if (vehicle == null || vehicleParts == null)
                emptyList()
            else if (vehicleParts.isEmpty())
                listOf<DetailsItem>(DetailsItem.VehicleItem(vehicle)) +
                        listOf(DetailsItem.NoAddedParts)
            else
                listOf<DetailsItem>(DetailsItem.VehicleItem(vehicle)) +
                        vehicleParts.map { DetailsItem.PartItem(it) }
}

sealed class DetailsItem {
    data class VehicleItem(val vehicle: Vehicle) : DetailsItem()
    data class PartItem(
        val part: VehiclePart,
    ) : DetailsItem()
    object NoAddedParts : DetailsItem()
}
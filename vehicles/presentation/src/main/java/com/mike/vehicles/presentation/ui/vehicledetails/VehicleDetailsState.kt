package com.mike.vehicles.presentation.ui.vehicledetails

import com.mike.core.presentation.utils.compose.OrientationType
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.models.VehiclePartStatus
import com.mike.domian.vehicles.models.VehicleParts

data class VehicleDetailsState(
    val vehicleId: Long = 0,
    val vehicle: Vehicle? = null,
    val vehicleParts: VehicleParts? = null,
    val showUpdateKmDialog: Boolean = false
) {
    fun displayList(addVehicleHeader: Boolean): List<DetailsItem> {
        if (vehicle == null || vehicleParts == null)
            return emptyList()

        val listBuilder = mutableListOf<DetailsItem>()

        if (addVehicleHeader)
            listBuilder.add(DetailsItem.VehicleItem(vehicle))

        if (vehicleParts.isEmpty())
            listBuilder.add(DetailsItem.NoAddedParts)
        else {
            val vehiclePartsItems = vehicleParts.map { DetailsItem.PartItem(
                part = it,
                partStatus = VehiclePartStatus.partStatus(
                    part = it,
                    currentVehicleKm = vehicle.currentKM
                )
            ) }
            listBuilder.addAll(vehiclePartsItems)
        }

        return listBuilder.toList()
    }
}

sealed class DetailsItem {
    data class VehicleItem(val vehicle: Vehicle) : DetailsItem()
    data class PartItem(
        val part: VehiclePart,
        val partStatus: VehiclePartStatus
    ) : DetailsItem()
    object NoAddedParts : DetailsItem()
}
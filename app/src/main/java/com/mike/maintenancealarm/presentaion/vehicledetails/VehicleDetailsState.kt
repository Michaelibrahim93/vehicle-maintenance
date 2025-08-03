package com.mike.maintenancealarm.presentaion.vehicledetails

import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleParts

data class VehicleDetailsState(
    val vehicle: Vehicle? = null,
    val vehicleParts: VehicleParts? = null
) {
    val displayList: List<Any>
        get() = if (vehicle == null || vehicleParts == null)
                emptyList()
            else
                listOf<Any>(vehicle) + vehicleParts
}
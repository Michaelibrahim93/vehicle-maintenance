package com.mike.maintenancealarm.presentation.vehicledetails

import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehiclePart

sealed class VehicleDetailsEvents {
    object OnBackClick : VehicleDetailsEvents()
    object AddNewPart : VehicleDetailsEvents()
    data class RenewPart(val part: VehiclePart) : VehicleDetailsEvents()
    data class UpdateVehicleKm(val vehicle: Vehicle) : VehicleDetailsEvents()
}
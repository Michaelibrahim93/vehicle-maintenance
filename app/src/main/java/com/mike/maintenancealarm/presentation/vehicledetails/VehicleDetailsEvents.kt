package com.mike.maintenancealarm.presentation.vehicledetails

import com.mike.maintenancealarm.domain.models.Vehicle
import com.mike.maintenancealarm.domain.models.VehiclePart

sealed class VehicleDetailsEvents {
    object OnBackClick : VehicleDetailsEvents()
    object AddNewPart : VehicleDetailsEvents()
    data class RenewPart(val part: VehiclePart) : VehicleDetailsEvents()
    data class UpdateVehicleKm(val vehicle: Vehicle) : VehicleDetailsEvents()
    object DismissUpdateKmDialog : VehicleDetailsEvents()
}
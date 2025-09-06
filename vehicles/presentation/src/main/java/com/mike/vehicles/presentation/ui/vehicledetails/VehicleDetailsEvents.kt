package com.mike.vehicles.presentation.ui.vehicledetails

import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.VehiclePart

sealed class VehicleDetailsEvents {
    object OnBackClick : VehicleDetailsEvents()
    object AddNewPart : VehicleDetailsEvents()
    data class RenewPart(val part: VehiclePart) : VehicleDetailsEvents()
    data class UpdateVehicleKm(val vehicle: Vehicle) : VehicleDetailsEvents()
    object DismissUpdateKmDialog : VehicleDetailsEvents()
}
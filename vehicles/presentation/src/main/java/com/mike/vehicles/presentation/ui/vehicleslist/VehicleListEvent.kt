package com.mike.vehicles.presentation.ui.vehicleslist

import com.mike.domian.vehicles.models.Vehicle

sealed class VehicleListEvent {
    data class NavigateToVehicleDetails(val vehicle: Vehicle) : VehicleListEvent()
    data object AddNewVehicle : VehicleListEvent()
}
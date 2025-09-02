package com.mike.maintenancealarm.presentation.vehicleslist

import com.mike.maintenancealarm.domain.vo.Vehicle

sealed class VehicleListEvent {
    data class NavigateToVehicleDetails(val vehicle: Vehicle) : VehicleListEvent()
    data object AddNewVehicle : VehicleListEvent()
}
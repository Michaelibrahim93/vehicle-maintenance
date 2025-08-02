package com.mike.maintenancealarm.presentaion.vehicleslist

import com.mike.maintenancealarm.data.vo.Vehicle

sealed class VehicleListEvent {
    data class NavigateToVehicleDetails(val vehicle: Vehicle) : VehicleListEvent()
    data object AddNewVehicle : VehicleListEvent()
}
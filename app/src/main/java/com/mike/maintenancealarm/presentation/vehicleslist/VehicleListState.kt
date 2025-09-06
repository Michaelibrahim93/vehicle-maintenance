package com.mike.maintenancealarm.presentation.vehicleslist

import com.mike.domian.vehicles.models.Vehicles

data class VehicleListState(
    val vehicles: Vehicles = emptyList()
)

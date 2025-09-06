package com.mike.vehicles.presentation.ui.vehicleslist

import com.mike.domian.vehicles.models.Vehicles

data class VehicleListState(
    val vehicles: Vehicles = emptyList()
)

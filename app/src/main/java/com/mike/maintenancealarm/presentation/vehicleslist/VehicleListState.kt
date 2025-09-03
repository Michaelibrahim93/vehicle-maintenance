package com.mike.maintenancealarm.presentation.vehicleslist

import com.mike.maintenancealarm.domain.models.Vehicles

data class VehicleListState(
    val vehicles: Vehicles = emptyList()
)

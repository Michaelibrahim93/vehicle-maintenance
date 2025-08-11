package com.mike.maintenancealarm.presentation.vehicleslist

import com.mike.maintenancealarm.data.vo.Vehicles

data class VehicleListState(
    val vehicles: Vehicles = emptyList()
)

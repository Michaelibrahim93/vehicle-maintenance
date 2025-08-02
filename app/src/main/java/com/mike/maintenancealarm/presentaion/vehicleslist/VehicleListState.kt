package com.mike.maintenancealarm.presentaion.vehicleslist

import com.mike.maintenancealarm.data.vo.Vehicles

data class VehicleListState(
    val vehicles: Vehicles = emptyList()
)

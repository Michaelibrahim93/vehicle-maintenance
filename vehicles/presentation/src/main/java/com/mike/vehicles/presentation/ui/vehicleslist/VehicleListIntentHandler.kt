package com.mike.vehicles.presentation.ui.vehicleslist

import androidx.navigation.NavController
import com.mike.core.presentation.navigation.Route

fun vehicleListIntentHandler(
    event: VehicleListEvent,
    rootNavController: NavController,
    navController: NavController
) {
    when (event) {
        is VehicleListEvent.NavigateToVehicleDetails -> {
            navController.navigate(Route.VehicleDetails(
                vehicleId = event.vehicle.id ?: 0
            ))
        }
        is VehicleListEvent.AddNewVehicle -> {
            rootNavController.navigate(route = Route.NewVehicle)
        }
    }
}
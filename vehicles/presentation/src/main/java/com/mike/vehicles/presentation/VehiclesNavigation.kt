package com.mike.vehicles.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.mike.core.presentation.navigation.Route
import com.mike.core.presentation.utils.compose.NavAnimationType
import com.mike.core.presentation.utils.compose.animatedComposable
import com.mike.vehicles.presentation.ui.newpart.NewVehiclePartComposable
import com.mike.vehicles.presentation.ui.newvehicle.NewVehicleComposable
import com.mike.vehicles.presentation.ui.vehicledetails.VehicleDetailsScreenComposable
import com.mike.vehicles.presentation.ui.vehicleslist.VehicleListComposable

fun NavGraphBuilder.vehiclesGraph(navController: NavController) {
    animatedComposable<Route.VehicleList>(
        navAnimationType = NavAnimationType.SLIDE_IN_HORIZONTALLY
    ) {
        VehicleListComposable(navController = navController)
    }

    animatedComposable<Route.NewVehicle>(
        navAnimationType = NavAnimationType.SLIDE_IN_FROM_BOTTOM
    ) {
        NewVehicleComposable(navController)
    }

    animatedComposable<Route.VehicleDetails>(
        navAnimationType = NavAnimationType.SLIDE_IN_HORIZONTALLY
    ) {
        VehicleDetailsScreenComposable(navController)
    }

    animatedComposable<Route.NewVehiclePart>(
        navAnimationType = NavAnimationType.SLIDE_IN_FROM_BOTTOM
    ) {
        NewVehiclePartComposable(navController)
    }
}
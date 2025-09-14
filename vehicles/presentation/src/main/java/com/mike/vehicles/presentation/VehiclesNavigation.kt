package com.mike.vehicles.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mike.core.presentation.navigation.Route
import com.mike.core.presentation.utils.compose.NavAnimationType
import com.mike.core.presentation.utils.compose.animatedComposable
import com.mike.vehicles.presentation.ui.newpart.NewVehiclePartComposable
import com.mike.vehicles.presentation.ui.newvehicle.NewVehicleComposable
import com.mike.vehicles.presentation.ui.vehicledetails.VehicleDetailsScreenComposable
import com.mike.vehicles.presentation.ui.vehicleslist.VehicleListComposable

fun NavGraphBuilder.vehiclesRootGraph(navController: NavController) {
    animatedComposable<Route.NewVehicle>(
        navAnimationType = NavAnimationType.SLIDE_IN_FROM_BOTTOM
    ) {
        NewVehicleComposable(navController)
    }

    animatedComposable<Route.NewVehiclePart>(
        navAnimationType = NavAnimationType.SLIDE_IN_FROM_BOTTOM
    ) {
        NewVehiclePartComposable(navController)
    }
}

fun NavGraphBuilder.vehiclesTabGraph(
    navController: NavController,
    rootNavController: NavController
) {
    composable<Route.VehicleList> {
        VehicleListComposable(
            rootNavController = rootNavController,
            navController = navController,
        )
    }

    composable<Route.VehicleDetails> {
        VehicleDetailsScreenComposable(
            rootNavController = rootNavController,
            navController = navController
        )
    }
}
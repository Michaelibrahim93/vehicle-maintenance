package com.mike.maintenancealarm.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.maintenancealarm.presentation.newpart.NewVehiclePartComposable
import com.mike.maintenancealarm.presentation.newvehicle.NewVehicleComposable
import com.mike.maintenancealarm.presentation.splash.SplashComposable
import com.mike.maintenancealarm.presentation.vehicledetails.VehicleDetailsScreenComposable
import com.mike.maintenancealarm.presentation.vehicleslist.VehicleListComposable
import com.mike.maintenancealarm.utils.compose.NavAnimationType
import com.mike.maintenancealarm.utils.compose.animatedComposable

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ) {
        composable<Route.Splash> {
            SplashComposable(navController = navController)
        }

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
}
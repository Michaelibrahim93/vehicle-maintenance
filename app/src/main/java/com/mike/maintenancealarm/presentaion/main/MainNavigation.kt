package com.mike.maintenancealarm.presentaion.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.maintenancealarm.presentaion.splash.DestinationSplashScreen
import com.mike.maintenancealarm.presentaion.splash.SplashComposable
import com.mike.maintenancealarm.presentaion.splash.SplashScreen
import com.mike.maintenancealarm.presentaion.splash.SplashViewModel
import com.mike.maintenancealarm.presentaion.vehicleslist.DestinationVehicleListScreen
import com.mike.maintenancealarm.presentaion.vehicleslist.VehicleListComposable

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DestinationSplashScreen
    ) {
        composable<DestinationSplashScreen> {
            SplashComposable(navController = navController)
        }

        composable<DestinationVehicleListScreen> {
            VehicleListComposable(navController = navController)
        }
    }
}
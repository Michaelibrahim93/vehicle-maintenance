package com.mike.maintenancealarm.presentaion.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.maintenancealarm.presentaion.newvehicle.DestinationNewVehicleScreen
import com.mike.maintenancealarm.presentaion.newvehicle.NewVehicleComposable
import com.mike.maintenancealarm.presentaion.splash.DestinationSplashScreen
import com.mike.maintenancealarm.presentaion.splash.SplashComposable
import com.mike.maintenancealarm.presentaion.splash.SplashScreen
import com.mike.maintenancealarm.presentaion.splash.SplashViewModel
import com.mike.maintenancealarm.presentaion.vehicleslist.DestinationVehicleListScreen
import com.mike.maintenancealarm.presentaion.vehicleslist.VehicleListComposable
import com.mike.maintenancealarm.utils.compose.NavAnimationType
import com.mike.maintenancealarm.utils.compose.animatedComposable

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

//        animatedComposable<DestinationVehicleListScreen>(
//            navAnimationType = NavAnimationType.SLIDE_IN_FROM_RIGHT
//        ) {
        composable<DestinationVehicleListScreen>{
            VehicleListComposable(navController = navController)
        }

//        animatedComposable<DestinationNewVehicleScreen>(
//            navAnimationType = NavAnimationType.SLIDE_IN_FROM_BOTTOM
//        ) {
        composable<DestinationNewVehicleScreen> {
            NewVehicleComposable(navController)
        }
    }
}
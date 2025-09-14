package com.mike.maintenancealarm.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mike.core.presentation.navigation.Route
import com.mike.core.presentation.utils.compose.NavAnimationType
import com.mike.core.presentation.utils.compose.animatedComposable
import com.mike.maintenancealarm.home.HomeScreenComposable
import com.mike.maintenancealarm.splash.SplashComposable
import com.mike.vehicles.presentation.vehiclesRootGraph

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

        animatedComposable<Route.Home>(
            navAnimationType = NavAnimationType.SLIDE_IN_HORIZONTALLY
        ) {
            HomeScreenComposable(
                rootNavController = navController
            )
        }

        vehiclesRootGraph(navController = navController)
    }
}
package com.mike.maintenancealarm.home.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mike.core.presentation.navigation.Route
import com.mike.profile.presentation.profileTabNavigation
import com.mike.vehicles.presentation.vehiclesTabGraph

@Composable
fun VehiclesTabNavigation(
    rootNavController: NavController
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.VehicleList
    ) {
        vehiclesTabGraph(
            navController = navController,
            rootNavController = rootNavController
        )
    }
}

@Composable
fun ProfileTabNavigation(
    rootNavController: NavController
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Profile
    ) {
        profileTabNavigation(
            navController = navController,
            rootNavController = rootNavController
        )
    }
}
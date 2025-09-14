package com.mike.maintenancealarm.home.navhosts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

sealed class HomeTabRoute {
    @Serializable
    object Vehicles : HomeTabRoute()
    @Serializable
    object Profile : HomeTabRoute()
}

@Composable
fun HomeNavHost(
    rootNavController: NavController,
    contentPadding: PaddingValues,
    tabNavController: NavHostController
) {
    NavHost(
        navController = tabNavController,
        startDestination = HomeTabRoute.Vehicles,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        composable<HomeTabRoute.Vehicles> {
            VehiclesTabNavigation(rootNavController)
        }
        composable<HomeTabRoute.Profile> {
            ProfileTabNavigation(rootNavController)
        }
    }
}
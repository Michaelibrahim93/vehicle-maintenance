package com.mike.profile.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mike.core.presentation.navigation.Route
import com.mike.profile.presentation.ui.ProfileScreenComposable

fun NavGraphBuilder.profileTabNavigation(
    rootNavController: NavController,
    navController: NavController
) {
    composable<Route.Profile> {
        ProfileScreenComposable(rootNavController, navController)
    }
}
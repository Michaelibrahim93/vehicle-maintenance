package com.mike.maintenancealarm.presentaion.vehicledetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@SuppressLint("ContextCastToActivity")
@Composable
fun VehicleDetailsScreenComposable(
    navController: NavController,
    viewModel: VehicleDetailsViewModel = hiltViewModel()
) {
    VehicleDetailsScreen(
        navController = navController,
        vehicleId = viewModel.vehicleId,
    )
}

@Composable
fun VehicleDetailsScreen(navController: NavController, vehicleId: Long) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Vehicle Details Screen for Vehicle ID: $vehicleId"
        )
    }
}
package com.mike.vehicles.presentation.ui.vehicledetails

import androidx.navigation.NavController
import com.mike.core.presentation.navigation.Route

fun vehicleDetailsIntentHandler(
    event: VehicleDetailsEvents,
    rootNavController: NavController,
    navController: NavController,
    viewModel: VehicleDetailsViewModel
) {
    when(event) {
        is VehicleDetailsEvents.OnBackClick ->
            navController.popBackStack()
        VehicleDetailsEvents.AddNewPart ->
            rootNavController.navigate(route = Route.NewVehiclePart(viewModel.vehicleId, null))
        is VehicleDetailsEvents.RenewPart ->
            rootNavController.navigate(route = Route.NewVehiclePart(viewModel.vehicleId, event.part.id))
        is VehicleDetailsEvents.UpdateVehicleKm ->
            viewModel.setShowUpdateDialog(true)
        VehicleDetailsEvents.DismissUpdateKmDialog ->
            viewModel.setShowUpdateDialog(false)
    }
}

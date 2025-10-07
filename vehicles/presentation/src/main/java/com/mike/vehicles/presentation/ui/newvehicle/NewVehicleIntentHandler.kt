package com.mike.vehicles.presentation.ui.newvehicle

import androidx.navigation.NavController

fun newVehicleIntentHandler(
    event: NewVehicleEvent,
    navController: NavController,
    viewModel: NewVehicleViewModel
) {
    when(event) {
        is NewVehicleEvent.OnBackClick -> {
            navController.popBackStack()
        }
        is NewVehicleEvent.OnVehicleImageChange ->
            viewModel.updateVehicleImage(event.image)
        is NewVehicleEvent.OnVehicleNameChange ->
            viewModel.updateVehicleName(event.name)
        is NewVehicleEvent.OnVehicleKmChange ->
            viewModel.updateVehicleKm(event.km)
        is NewVehicleEvent.OnSaveClick -> viewModel.validateAndSave()
        NewVehicleEvent.OnImageClick -> {}
    }
}
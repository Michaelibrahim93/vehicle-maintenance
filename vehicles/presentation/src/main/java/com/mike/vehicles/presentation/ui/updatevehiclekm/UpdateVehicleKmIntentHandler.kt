package com.mike.vehicles.presentation.ui.updatevehiclekm

import timber.log.Timber

fun updateVehicleKmIntentHandler(
    event: UpdateVehicleKmEvent,
    viewModel: UpdateVehicleKmViewModel,
    onDismiss: () -> Unit
) {
    Timber.d("updateVehicleKmIntentHandler: $event")
    when(event) {
        UpdateVehicleKmEvent.DismissSheet -> onDismiss
        is UpdateVehicleKmEvent.OnKmChange ->
            viewModel.onKmChange(event.km)
        is UpdateVehicleKmEvent.OnSaveClick -> viewModel.validateAndSave()
    }
}
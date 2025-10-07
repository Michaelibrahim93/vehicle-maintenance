package com.mike.vehicles.presentation.ui.newpart

import androidx.navigation.NavController
import com.mike.vehicles.presentation.ui.newpart.events.NewVehiclePartEvent
import com.mike.vehicles.presentation.ui.newpart.uistate.NewVehiclePartUiState
import com.mike.vehicles.presentation.ui.newpart.uistate.updateDeploymentDate
import com.mike.vehicles.presentation.ui.newpart.uistate.updateDeploymentKm
import com.mike.vehicles.presentation.ui.newpart.uistate.updateLifeSpanInKm
import com.mike.vehicles.presentation.ui.newpart.uistate.updateLifeSpanInMonths
import com.mike.vehicles.presentation.ui.newpart.uistate.updatePartName
import com.mike.vehicles.presentation.ui.newpart.uistate.updatePrice
import com.mike.vehicles.presentation.ui.newpart.uistate.updateSupplier

fun newVehiclePartIntentHandler(
    event: NewVehiclePartEvent,
    navController: NavController,
    viewModel: NewVehiclePartViewModel
) {
    when(event) {
        is NewVehiclePartEvent.OnBackClick ->
            navController.popBackStack()
        is NewVehiclePartEvent.OnPartNameChange ->
            viewModel.updatePartName(event.partName)
        is NewVehiclePartEvent.OnDeploymentDateChange ->
            viewModel.updateDeploymentDate(event.deploymentDate)
        is NewVehiclePartEvent.OnDeploymentKmChange ->
            viewModel.updateDeploymentKm(event.deploymentKm)
        is NewVehiclePartEvent.OnLifeSpanInMonthsChange ->
            viewModel.updateLifeSpanInMonths(event.lifeSpanInMonths)
        is NewVehiclePartEvent.OnLifeSpanInKmChange ->
            viewModel.updateLifeSpanInKm(event.lifeSpanInKm)
        is NewVehiclePartEvent.OnSupplierChange ->
            viewModel.updateSupplier(event.supplier)
        is NewVehiclePartEvent.OnPriceChange ->
            viewModel.updatePrice(event.price)
        is NewVehiclePartEvent.OnSaveClick ->
            viewModel.validateAndSave()
        is NewVehiclePartEvent.OnUpdateVehicleKmClick ->
            viewModel.updateVehicleKm()
    }
}

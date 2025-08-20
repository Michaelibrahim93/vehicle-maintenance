package com.mike.maintenancealarm.presentation.newpart.events

import java.util.Date

sealed class NewVehiclePartEvent {
    data class OnPartNameChange(val partName: String) : NewVehiclePartEvent()
    data class OnDeploymentDateChange(val deploymentDate: Date) : NewVehiclePartEvent()
    data class OnDeploymentKmChange(val deploymentKm: String) : NewVehiclePartEvent()
    data class OnLifeSpanInMonthsChange(val lifeSpanInMonths: String) : NewVehiclePartEvent()
    data class OnLifeSpanInKmChange(val lifeSpanInKm: String) : NewVehiclePartEvent()
    data class OnSupplierChange(val supplier: String) : NewVehiclePartEvent()
    data class OnPriceChange(val price: String) : NewVehiclePartEvent()
    object OnSaveClick : NewVehiclePartEvent()
    object OnBackClick : NewVehiclePartEvent()
    object OnUpdateVehicleKmClick : NewVehiclePartEvent()
}

sealed class NewVehiclePartUiAction {
    data class ShowError(val throwable: Throwable) : NewVehiclePartUiAction()
    data object ShowSuccess : NewVehiclePartUiAction()
    data class ValidationCarKm(
        val vehicleKm: Double,
        val partKm: Double
    ) : NewVehiclePartUiAction()
}
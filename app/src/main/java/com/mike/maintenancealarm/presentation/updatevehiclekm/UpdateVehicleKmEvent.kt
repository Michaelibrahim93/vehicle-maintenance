package com.mike.maintenancealarm.presentation.updatevehiclekm

sealed class UpdateVehicleKmEvent {
    data class OnKmChange(val km: String) : UpdateVehicleKmEvent()
    object OnSaveClick : UpdateVehicleKmEvent()
    object DismissSheet : UpdateVehicleKmEvent()
}

sealed class UpdateVehicleKmUiAction {
    data class ShowError(val throwable: Throwable) : UpdateVehicleKmUiAction()
    data object ShowSuccess : UpdateVehicleKmUiAction()
}
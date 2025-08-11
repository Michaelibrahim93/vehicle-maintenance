package com.mike.maintenancealarm.presentation.newvehicle

sealed class NewVehicleEvent {
    data class OnVehicleImageChange(val image: String) : NewVehicleEvent()
    data class OnVehicleNameChange(val name: String) : NewVehicleEvent()
    data class OnVehicleKmChange(val km: String) : NewVehicleEvent()
    object OnImageClick : NewVehicleEvent()
    object OnSaveClick : NewVehicleEvent()
    object OnBackClick : NewVehicleEvent()
}

sealed class NewVehicleUiAction {
    data class ShowError(val throwable: Throwable) : NewVehicleUiAction()
    data object ShowSuccess : NewVehicleUiAction()
}
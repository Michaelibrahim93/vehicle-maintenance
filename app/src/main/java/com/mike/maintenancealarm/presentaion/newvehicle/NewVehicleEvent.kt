package com.mike.maintenancealarm.presentaion.newvehicle

sealed class NewVehicleEvent {
    data class OnVehicleImageChange(val image: String) : NewVehicleEvent()
    data class OnVehicleNameChange(val name: String) : NewVehicleEvent()
    data class OnVehicleKmChange(val km: String) : NewVehicleEvent()
    object OnSaveClick : NewVehicleEvent()
    object OnBackClick : NewVehicleEvent()
}

sealed class NewVehicleUiAction {
    data class ShowError(val throwable: Throwable) : NewVehicleUiAction()
    data object ShowSuccess : NewVehicleUiAction()
}
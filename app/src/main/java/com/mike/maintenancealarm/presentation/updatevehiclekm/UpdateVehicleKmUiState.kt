package com.mike.maintenancealarm.presentation.updatevehiclekm

import com.mike.maintenancealarm.data.vo.ValidationInput

data class UpdateVehicleKmUiState(
    val vehicleId: Long = 0,
    val vehicleName: String = "",
    val isLoading: Boolean = false,
    val inputKm: ValidationInput<String> = ValidationInput(""),
) {
    fun setKm(km: String) = copy(
        inputKm = inputKm.copy(input = km)
    )

    companion object {
        const val KEY_INPUT_KM = "inputKm"
    }
}
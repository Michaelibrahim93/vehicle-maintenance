package com.mike.vehicles.presentation.ui.updatevehiclekm

import com.mike.core.domain.utils.validator.ValidationInput

data class UpdateVehicleKmUiState(
    val vehicleId: Long = 0,
    val vehicleName: String = "",
    val isLoading: Boolean = false,
    val inputKm: ValidationInput<String> = ValidationInput(""),
) {
    fun setKm(km: String) = copy(
        inputKm = inputKm.copy(input = km)
    )

    fun toValidationMap() = mapOf(
        KEY_INPUT_KM to inputKm.input
    )

    fun setValidationErrors(validationErrors: Map<String, Int>) = copy(
        inputKm = inputKm.copy(errorStringRes = validationErrors[KEY_INPUT_KM])
    )


    companion object {
        const val KEY_INPUT_KM = "inputKm"
    }
}
package com.mike.vehicles.presentation.ui.newvehicle

import com.mike.core.domain.utils.validator.ValidationInput
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.models.VehicleStatus
import java.util.Date

data class NewVehicleState(
    val vehicleName: ValidationInput<String> = ValidationInput(""),
    val vehicleImage: String? = null,
    val currentKM: ValidationInput<String> = ValidationInput(""),
    val isLoading: Boolean = false,
) {
    fun setLoading(isLoading: Boolean): NewVehicleState {
        return copy(isLoading = isLoading)
    }

    fun setVehicleName(name: String): NewVehicleState {
        return copy(vehicleName = vehicleName.copy(input = name))
    }

    fun setVehicleImage(image: String?): NewVehicleState {
        return copy(vehicleImage = image)
    }

    fun setCurrentKM(km: String): NewVehicleState {
        return copy(currentKM = currentKM.copy(input = km))
    }

    fun toVehicle() = Vehicle(
        id = null,
        vehicleName = vehicleName.input,
        vehicleImage = vehicleImage,
        lastKmUpdate = Date(),
        currentKM = currentKM.input.toDouble(),
        vehicleStatus = VehicleStatus.OK
    )

    fun toValidationMap(): Map<String, Any> {
        return mapOf(
            KEY_VEHICLE_NAME to vehicleName.input,
            KEY_CURRENT_KM to currentKM.input
        )
    }

    fun updateValidationErrors(validationResult: Map<String, Int>): NewVehicleState {
        return copy(
            vehicleName = vehicleName.copy(errorStringRes = validationResult[KEY_VEHICLE_NAME]),
            currentKM = currentKM.copy(errorStringRes = validationResult[KEY_CURRENT_KM])
        )
    }

    companion object {
        const val KEY_VEHICLE_NAME = "vehicleName"
        const val KEY_CURRENT_KM = "currentKM"
    }
}
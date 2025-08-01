package com.mike.maintenancealarm.presentaion.newvehicle

import com.mike.maintenancealarm.data.vo.ValidationInput
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import java.util.Date

data class NewVehicleState(
    val vehicleName: ValidationInput<String> = ValidationInput<String>(""),
    val vehicleImage: String? = null,
    val currentKM: ValidationInput<String> = ValidationInput<String>(""),
    val isLoading: Boolean = false,
) {
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

    fun setValidationErrors(validationResult: Map<String, String>): NewVehicleState {
        return copy(
            vehicleName = vehicleName.copy(errorMessage = validationResult[KEY_VEHICLE_NAME]),
            currentKM = currentKM.copy(errorMessage = validationResult[KEY_CURRENT_KM])
        )
    }

    companion object {
        const val KEY_VEHICLE_NAME = "vehicleName"
        const val KEY_CURRENT_KM = "currentKM"
    }
}
package com.mike.maintenancealarm.presentation.newpart.uistate

import com.mike.maintenancealarm.domain.vo.ValidationInput
import com.mike.maintenancealarm.domain.vo.Vehicle
import com.mike.maintenancealarm.domain.vo.VehiclePart
import java.util.Date

data class NewVehiclePartUiState(
    val isLoading: Boolean = false,
    val partName: ValidationInput<String> = ValidationInput(""),
    val deploymentDate: ValidationInput<Date?> = ValidationInput(null),
    val deploymentKm: ValidationInput<String> = ValidationInput(""),
    val lifeSpanInMonths: ValidationInput<String> = ValidationInput(""),
    val lifeSpanInKm: ValidationInput<String> = ValidationInput(""),
    val supplier: ValidationInput<String> = ValidationInput(""),
    val price: ValidationInput<String> = ValidationInput(""),
    val vehicleId: Long = 0L,
    val partToBeRenewed: VehiclePart? = null
) {
    val isEditing: Boolean
        get() = partToBeRenewed != null
    companion object {
        const val KEY_PART_NAME = "partName"
        const val KEY_DEPLOYMENT_DATE = "deploymentDate"
        const val KEY_DEPLOYMENT_KM = "deploymentKm"
        const val KEY_LIFE_SPAN_IN_MONTHS = "lifeSpanInMonths"
        const val KEY_LIFE_SPAN_IN_KM = "lifeSpanInKm"
        const val KEY_SUPPLIER = "supplier"
        const val KEY_PRICE = "price"

        fun initialState(
            vehicleId: Long,
            vehicle: Vehicle? = null,
            partToBeRenewed: VehiclePart? = null
        ): NewVehiclePartUiState {
            return NewVehiclePartUiState(
                vehicleId = vehicleId,
                partToBeRenewed = partToBeRenewed,
                deploymentKm = ValidationInput(vehicle?.currentKM?.toString() ?: ""),
                partName = ValidationInput(partToBeRenewed?.partName ?: ""),
                lifeSpanInMonths = ValidationInput(partToBeRenewed?.lifeSpan?.months?.toString() ?: ""),
                lifeSpanInKm = ValidationInput(partToBeRenewed?.lifeSpan?.km?.toString() ?: ""),
                supplier = ValidationInput(partToBeRenewed?.supplier ?: ""),
                price = ValidationInput(partToBeRenewed?.price?.toString() ?: ""),
            )
        }
    }
}
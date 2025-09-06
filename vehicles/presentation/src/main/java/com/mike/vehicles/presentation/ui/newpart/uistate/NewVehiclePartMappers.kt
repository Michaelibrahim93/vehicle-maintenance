package com.mike.vehicles.presentation.ui.newpart.uistate

import com.mike.domian.vehicles.models.LifeSpan
import com.mike.domian.vehicles.models.VehiclePart
import java.util.Date

fun NewVehiclePartUiState.toVehiclePart(): VehiclePart {
    return VehiclePart(
        id = partToBeRenewed?.id ?: 0,
        vehicleId = vehicleId,
        partName = partName.input,
        deploymentDate = deploymentDate.input ?: Date(),
        deploymentKM = deploymentKm.input.toDouble(),
        lifeSpan = LifeSpan(
            km = lifeSpanInKm.input.toDouble(),
            months = lifeSpanInMonths.input.toInt()
        ),
        supplier = supplier.input.ifBlank { null },
        price = if (price.input.isBlank()) null else price.input.toDouble()
    )
}

fun NewVehiclePartUiState.toValidationMap(): Map<String, Any?> {
    return mapOf<String, Any?>(
        NewVehiclePartUiState.KEY_PART_NAME to partName.input,
        NewVehiclePartUiState.KEY_DEPLOYMENT_DATE to deploymentDate.input,
        NewVehiclePartUiState.KEY_DEPLOYMENT_KM to deploymentKm.input,
        NewVehiclePartUiState.KEY_LIFE_SPAN_IN_MONTHS to lifeSpanInMonths.input,
        NewVehiclePartUiState.KEY_LIFE_SPAN_IN_KM to lifeSpanInKm.input,
        NewVehiclePartUiState.KEY_SUPPLIER to supplier.input,
        NewVehiclePartUiState.KEY_PRICE to price.input
    )
}
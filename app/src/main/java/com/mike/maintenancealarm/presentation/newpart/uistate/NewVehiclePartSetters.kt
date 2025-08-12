package com.mike.maintenancealarm.presentation.newpart.uistate

import java.util.Date

fun NewVehiclePartUiState.updateLoading(isLoading: Boolean): NewVehiclePartUiState {
    return this.copy(isLoading = isLoading)
}

fun NewVehiclePartUiState.updatePartName(name: String): NewVehiclePartUiState {
    return this.copy(partName = this.partName.copy(input = name))
}

fun NewVehiclePartUiState.updateDeploymentDate(date: Date?): NewVehiclePartUiState {
    return this.copy(deploymentDate = this.deploymentDate.copy(input = date))
}

fun NewVehiclePartUiState.updateDeploymentKm(km: String): NewVehiclePartUiState {
    return this.copy(deploymentKm = this.deploymentKm.copy(input = km))
}

fun NewVehiclePartUiState.updateLifeSpanInMonths(months: String): NewVehiclePartUiState {
    return this.copy(lifeSpanInMonths = this.lifeSpanInMonths.copy(input = months))
}

fun NewVehiclePartUiState.updateLifeSpanInKm(km: String): NewVehiclePartUiState {
    return this.copy(lifeSpanInKm = this.lifeSpanInKm.copy(input = km))
}

fun NewVehiclePartUiState.updateSupplier(supplier: String): NewVehiclePartUiState {
    return this.copy(supplier = this.supplier.copy(input = supplier))
}

fun NewVehiclePartUiState.updatePrice(price: String): NewVehiclePartUiState {
    return this.copy(price = this.price.copy(input = price))
}

fun NewVehiclePartUiState.updateValidationErrors(validationResult: Map<String, Int>): NewVehiclePartUiState {
    return this.copy(
        partName = this.partName.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_PART_NAME]
        ),
        deploymentDate = this.deploymentDate.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_DEPLOYMENT_DATE]
        ),
        deploymentKm = this.deploymentKm.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_DEPLOYMENT_KM]
        ),
        lifeSpanInMonths = this.lifeSpanInMonths.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_LIFE_SPAN_IN_MONTHS]
        ),
        lifeSpanInKm = this.lifeSpanInKm.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_LIFE_SPAN_IN_KM]
        ),
        supplier = this.supplier.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_SUPPLIER]
        ),
        price = this.price.copy(
            errorStringRes = validationResult[NewVehiclePartUiState.KEY_PRICE]
        )
    )
}
package com.mike.maintenancealarm.presentation.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mike.domian.vehicles.models.VehiclePartStatus
import com.mike.domian.vehicles.models.VehicleStatus
import com.mike.maintenancealarm.presentation.theme.*

@Composable
fun partStatusColor(vehicleStatus: VehicleStatus): Color {
    return when (vehicleStatus) {
        VehicleStatus.OK -> if (isSystemInDarkTheme()) StatusOkDark else StatusOkLight
        VehicleStatus.HAS_NEAR_EXPIRATION -> if (isSystemInDarkTheme()) StatusNearExpirationDark else StatusNearExpirationLight
        VehicleStatus.HAS_EXPIRED -> if (isSystemInDarkTheme()) StatusExpiredDark else StatusExpiredLight
    }
}

@Composable
fun partStatusColor(partStatus: VehiclePartStatus): Color {
    return when (partStatus) {
        VehiclePartStatus.OK -> if (isSystemInDarkTheme()) StatusOkDark else StatusOkLight
        VehiclePartStatus.NEAR_EXPIRATION -> if (isSystemInDarkTheme()) StatusNearExpirationDark else StatusNearExpirationLight
        VehiclePartStatus.EXPIRED -> if (isSystemInDarkTheme()) StatusExpiredDark else StatusExpiredLight
    }
}

@Composable
fun secondaryTextColor(): Color {
    return if (isSystemInDarkTheme())
        SecondaryTextDark
    else
        SecondaryTextLight
}
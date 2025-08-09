package com.mike.maintenancealarm.presentaion.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mike.maintenancealarm.data.vo.VehiclePartStatus
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.presentaion.theme.*

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
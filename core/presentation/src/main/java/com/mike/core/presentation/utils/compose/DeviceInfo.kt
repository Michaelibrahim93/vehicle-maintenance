package com.mike.core.presentation.utils.compose

import android.content.res.Configuration
import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

enum class DeviceType {
    MOBILE, TABLET, DESKTOP
}

enum class OrientationType {
    PORTRAIT, LANDSCAPE
}

data class DeviceInfo(
    val deviceType: DeviceType,
    val orientation: OrientationType
)

@Composable
fun rememberDeviceInfo(): DeviceInfo {
    val configuration = LocalConfiguration.current

    val orientation = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> OrientationType.LANDSCAPE
        else -> OrientationType.PORTRAIT
    }

    // Smallest width in dp is a reliable way to differentiate mobile vs tablet
    val smallestWidthDp = configuration.smallestScreenWidthDp
    val deviceType = when {
        smallestWidthDp >= 840 -> DeviceType.DESKTOP // chromeOS, desktop-like
        smallestWidthDp >= 600 -> DeviceType.TABLET
        else -> DeviceType.MOBILE
    }

    return DeviceInfo(deviceType, orientation)
}

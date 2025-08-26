package com.mike.maintenancealarm.presentation.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

//        requestedOrientation = if (resources.configuration.smallestScreenWidthDp < 600)
//            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        else
//            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        enableEdgeToEdge()
        setContent {
            MaintenanceAlarmTheme {
                MainNavigation()
            }
        }
        splashScreen.setKeepOnScreenCondition { false }
    }
}
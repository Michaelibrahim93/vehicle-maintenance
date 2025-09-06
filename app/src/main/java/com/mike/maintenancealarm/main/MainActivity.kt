package com.mike.maintenancealarm.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.mike.core.presentation.theme.MaintenanceAlarmTheme
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

        Firebase.crashlytics.setCustomKey("user_id", "test_user")
    }
}
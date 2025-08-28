package com.mike.maintenancealarm.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class Main2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        enableEdgeToEdge()
        setContent {
            val viewModel: Main2VM = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.print()
                viewModel.updateUserId()
                viewModel.print()
            }
            MaintenanceAlarmTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Hello World! Activity 2"
                    )
                }
            }
        }
        splashScreen.setKeepOnScreenCondition { false }
    }
}
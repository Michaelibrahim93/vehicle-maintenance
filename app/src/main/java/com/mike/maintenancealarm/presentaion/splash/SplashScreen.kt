package com.mike.maintenancealarm.presentaion.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentaion.theme.MaintenanceAlarmTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue

@Serializable
data object DestinationSplashScreen

data class SplashScreenState(
    val isLoading: Boolean = false
)

sealed class SplashUiAction {
    object NavigateToVehiclesList : SplashUiAction()
}

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier,
    stateFlow: StateFlow<SplashScreenState>,
    actionsFlow: SharedFlow<SplashUiAction>
) {
    val state: SplashScreenState by stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(actionsFlow) {
        handleViewModelActions(
            actionsFlow = actionsFlow,
            navController = navController
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
            .background(Color.Green)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Splash Screen",
            modifier = modifier.align(Alignment.Center)
        )

        if (state.isLoading)
            CircularProgressIndicator(
                modifier = modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
    }
}

suspend fun handleViewModelActions(
    actionsFlow: SharedFlow<SplashUiAction>,
    navController: NavController
) {
    actionsFlow.collect { action ->
        when (action) {
            is SplashUiAction.NavigateToVehiclesList -> {
                Log.d("SplashScreen", "Navigating to Vehicles List")
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    MaintenanceAlarmTheme {
        SplashScreen(
            navController = NavController(context = LocalContext.current),
            modifier = Modifier.fillMaxSize(),
            stateFlow = MutableStateFlow(SplashScreenState(isLoading = true)),
            actionsFlow = MutableSharedFlow<SplashUiAction>()
        )
    }
}
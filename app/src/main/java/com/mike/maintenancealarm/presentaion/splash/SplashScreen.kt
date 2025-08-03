package com.mike.maintenancealarm.presentaion.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mike.maintenancealarm.presentaion.main.Route
import com.mike.maintenancealarm.utils.compose.ObserveEvent
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

data class SplashScreenState(
    val isLoading: Boolean = false
)

sealed class SplashUiAction {
    data object NavigateToVehiclesList : SplashUiAction()
}

@Composable
fun SplashComposable(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    SplashScreen(
        navController = navController,
        modifier = Modifier,
        stateFlow = viewModel.state,
        actionsFlow = viewModel.actionFlow
    )
}

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier,
    stateFlow: StateFlow<SplashScreenState>,
    actionsFlow: Flow<SplashUiAction>
) {
    val state: SplashScreenState by stateFlow.collectAsStateWithLifecycle()

    ObserveEvent(actionsFlow) {
        handleViewModelActions(
            action = it,
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

fun handleViewModelActions(
    action: SplashUiAction,
    navController: NavController
) {
    when (action) {
        is SplashUiAction.NavigateToVehiclesList -> {
            Timber.tag("SplashScreen").d("Navigating to Vehicles List")
            navController.navigate(Route.VehicleList) {
                popUpTo(Route.Splash) {
                    inclusive = true
                }
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
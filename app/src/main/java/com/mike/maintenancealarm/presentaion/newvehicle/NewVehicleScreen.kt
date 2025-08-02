package com.mike.maintenancealarm.presentaion.newvehicle

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentaion.theme.BIG_BUTTON_HEIGHT
import com.mike.maintenancealarm.presentaion.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentaion.theme.SPACE_SCREEN_H
import com.mike.maintenancealarm.presentaion.theme.SPACING_EXTRA_LARGE
import com.mike.maintenancealarm.presentaion.theme.SPACING_LARGE
import com.mike.maintenancealarm.presentaion.theme.SPACING_LARGE_PLUS
import com.mike.maintenancealarm.presentaion.theme.SPACING_SMALL
import com.mike.maintenancealarm.utils.compose.ObserveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

@Serializable
data object DestinationNewVehicleScreen

@Composable
fun NewVehicleComposable(
    navController: NavController,
    viewModel: NewVehicleViewModel = hiltViewModel()
) {
    NewVehicleScreen(
        navController = navController,
        actionFlow = viewModel.actionFlow,
        fState = viewModel.state,
        onEvent = { event ->
            when(event) {
                is NewVehicleEvent.OnBackClick -> {
                    navController.popBackStack()
                }
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun NewVehicleScreen(
    navController: NavController,
    actionFlow: Flow<NewVehicleUiAction>,
    fState: Flow<NewVehicleState>,
    onEvent: (NewVehicleEvent) -> Unit
) {
    val state: NewVehicleState = fState.collectAsStateWithLifecycle(initialValue = NewVehicleState()).value
    val context = LocalContext.current
    ObserveEvent(actionFlow) {
        handleViewModelActions(
            action = it,
            navController = navController,
            context = context
        )
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = { NewVehicleTopBar(
            onBackClick = { onEvent(NewVehicleEvent.OnBackClick) }
        ) }
    ) { contentPadding ->
        NewVehicleContent(
            state = state,
            modifier = Modifier.fillMaxSize().padding(contentPadding),
            onEvent = onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewVehicleTopBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.new_vehicle_title)) },
        navigationIcon = {
            IconButton(onClick = { onBackClick }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    )
}

@Composable
fun NewVehicleContent(
    state: NewVehicleState,
    modifier: Modifier,
    onEvent: (NewVehicleEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = state.vehicleImage,
            contentDescription = "Vehicle Image",
            placeholder = painterResource(R.drawable.ic_car_placeholder),
            error = painterResource(R.drawable.ic_car_placeholder),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(top = SPACING_EXTRA_LARGE)
                .width(100.dp)
                .height(100.dp)
                .clip(CircleShape)
                .border(2.dp, colorResource(R.color.image_foreground), CircleShape)
                .padding(6.dp)
                .align(Alignment.CenterHorizontally)
                .clickable{
                    onEvent(NewVehicleEvent.OnImageClick)
                }
        )

        TextField(
            value = state.vehicleName.input,
            onValueChange = { onEvent(NewVehicleEvent.OnVehicleNameChange(it)) },
            label = { Text(text = stringResource(id = R.string.vehicle_name_hint)) },
            modifier = Modifier.fillMaxWidth().padding(SPACING_LARGE),
            singleLine = true,
            isError = !state.vehicleName.isValid(),
            supportingText = {
                val text = state.vehicleName.errorMessage(LocalContext.current)
                if (!text.isNullOrEmpty()) {
                    Text(text = text, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        TextField(
            value = state.currentKM.input,
            onValueChange = { onEvent(NewVehicleEvent.OnVehicleKmChange(it)) },
            label = { Text(text = stringResource(id = R.string.vehicle_total_km)) },
            modifier = Modifier.fillMaxWidth().padding(
                start = SPACE_SCREEN_H,
                end = SPACE_SCREEN_H,
                top = SPACING_SMALL
            ),
            singleLine = true,
            isError = !state.currentKM.isValid(),
            supportingText = {
                val text = state.currentKM.errorMessage(LocalContext.current)
                if (!text.isNullOrEmpty()) {
                    Text(text = text, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Box(
            modifier = Modifier.weight(1.0f)
        )
        Button(
            modifier = Modifier
                .padding(horizontal = SPACE_SCREEN_H, vertical = SPACING_LARGE_PLUS)
                .fillMaxWidth()
                .height(BIG_BUTTON_HEIGHT),
            enabled = !state.isLoading,
            onClick = { onEvent(NewVehicleEvent.OnSaveClick) },
            content = {
                if (state.isLoading){
                    Text(
                        text = stringResource(id = R.string.saving),
                        style = MaterialTheme.typography.titleMedium
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.save),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
        )
    }
}

fun handleViewModelActions(
    action: NewVehicleUiAction,
    navController: NavController,
    context: Context
) {
    when (action) {
        is NewVehicleUiAction.ShowError -> {
            // Show error message to the user
        }
        NewVehicleUiAction.ShowSuccess -> {
            Toast.makeText(context, R.string.vehicle_added_successfully, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
    }
}

@Preview
@Composable
fun NewVehicleScreenPreview() {
    MaintenanceAlarmTheme {
        NewVehicleScreen(
            navController = NavController(context = LocalContext.current),
            actionFlow = MutableSharedFlow<NewVehicleUiAction>(),
            fState = MutableStateFlow(NewVehicleState()),
            onEvent = {}
        )
    }
}
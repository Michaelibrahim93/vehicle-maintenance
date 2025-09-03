package com.mike.maintenancealarm.presentation.updatevehiclekm

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentation.core.error.handleUiError
import com.mike.maintenancealarm.presentation.core.views.InputFieldView
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentation.theme.SPACING_LARGE
import com.mike.maintenancealarm.presentation.theme.SPACING_LARGE_PLUS
import com.mike.maintenancealarm.utils.compose.LogCurrentScreen
import com.mike.maintenancealarm.utils.compose.ObserveUiAction
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UpdateVehicleKmScreenComposable(
    vehicleId: Long,
    onDismiss: () -> Unit
) {
    val factory: UpdateVehicleKmViewModel.Factory = EntryPointAccessors.fromApplication(
        context = LocalContext.current.applicationContext,
        entryPoint = ViewModelEntryPoint::class.java
    ).updateVehicleKmFactory()

    val viewModel: UpdateVehicleKmViewModel = viewModel {
        factory.create(vehicleId)
    }

    UpdateVehicleKmScreen(
        uiState = viewModel.uiState,
        actionFlow = viewModel.actionFlow,
        onEvent = {event ->
            when(event) {
                is UpdateVehicleKmEvent.DismissSheet -> onDismiss()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateVehicleKmScreen(
    uiState: Flow<UpdateVehicleKmUiState>,
    actionFlow: Flow<UpdateVehicleKmUiAction>,
    onEvent: (UpdateVehicleKmEvent) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    LogCurrentScreen("UpdateVehicleKmScreen")

    ObserveUiAction(actionFlow) {
        handleViewModelActions(
            context = context,
            uiAction = it,
            onEvent = onEvent
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onEvent(UpdateVehicleKmEvent.DismissSheet) },
        sheetState = sheetState
    ) {
        UpdateVehicleKmScreenContent(
            uiState = uiState,
            onEvent = onEvent
        )
    }
}

@Composable
fun UpdateVehicleKmScreenContent(
    uiState: Flow<UpdateVehicleKmUiState>,
    onEvent: (UpdateVehicleKmEvent) -> Unit
) {
    val state: UpdateVehicleKmUiState by uiState.collectAsStateWithLifecycle(initialValue = UpdateVehicleKmUiState())
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.vehicleName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = SPACING_LARGE_PLUS, start = SPACING_LARGE, end = SPACING_LARGE)
        )

        InputFieldView(
            modifier = Modifier
                .padding(top = SPACING_LARGE, start = SPACING_LARGE, end = SPACING_LARGE)
                .fillMaxWidth(),
            value = state.inputKm.input,
            onValueChange = {text -> onEvent(UpdateVehicleKmEvent.OnKmChange(text))},
            isError = !state.inputKm.isValid(),
            supportingText = state.inputKm.errorMessage(LocalContext.current),
            label = stringResource(R.string.vehicle_km_title)
        )

        Button(
            modifier = Modifier
                .padding(top = SPACING_LARGE, start = SPACING_LARGE, end = SPACING_LARGE)
                .fillMaxWidth(),
            onClick = { onEvent(UpdateVehicleKmEvent.OnSaveClick) },
            enabled = !state.isLoading
        ) {
            Text(
                text = if (state.isLoading) stringResource(R.string.saving)
                else stringResource(R.string.save),
            )
        }
    }
}


private fun handleViewModelActions(
    context: Context,
    uiAction: UpdateVehicleKmUiAction,
    onEvent: (UpdateVehicleKmEvent) -> Unit
) {
    when(uiAction) {
        is UpdateVehicleKmUiAction.ShowError -> {
            handleUiError(context, uiAction.throwable)
        }
        is UpdateVehicleKmUiAction.ShowSuccess -> {
            Toast.makeText(context, R.string.vehicle_km_updated_successfully, Toast.LENGTH_SHORT).show()
            onEvent(UpdateVehicleKmEvent.DismissSheet)
        }
    }
}

@Preview
@Composable
fun UpdateVehicleKmScreenContentPreview() {
    MaintenanceAlarmTheme {
        Scaffold { contentPadding ->
            Box(Modifier.padding(contentPadding)) {
                UpdateVehicleKmScreen(
                    uiState = flowOf(UpdateVehicleKmUiState(
                        vehicleName = "Mazda Al Roumi"
                    )),
                    onEvent = {event -> TODO()},
                    actionFlow = flowOf(UpdateVehicleKmUiAction.ShowSuccess)
                )
            }
        }
    }
}
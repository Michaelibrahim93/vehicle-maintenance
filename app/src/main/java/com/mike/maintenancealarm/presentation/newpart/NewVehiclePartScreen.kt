package com.mike.maintenancealarm.presentation.newpart

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentation.core.error.handleUiError
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartEvent
import com.mike.maintenancealarm.presentation.newpart.events.NewVehiclePartUiAction
import com.mike.maintenancealarm.presentation.newpart.uistate.NewVehiclePartUiState
import com.mike.maintenancealarm.presentation.core.views.DateSelectorView
import com.mike.maintenancealarm.presentation.core.views.InputFieldView
import com.mike.maintenancealarm.presentation.theme.BIG_BUTTON_HEIGHT
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentation.theme.SPACE_SCREEN_H
import com.mike.maintenancealarm.presentation.theme.SPACING_LARGE_PLUS
import com.mike.maintenancealarm.presentation.theme.SPACING_SMALL
import com.mike.maintenancealarm.utils.compose.ObserveUiAction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun NewVehiclePartComposable(
    navController: NavController
) {
    val viewModel: NewVehiclePartViewModel = hiltViewModel()
    NewVehiclePartScreen(
        navController = navController,
        uiStateFlow = viewModel.state,
        onUiAction = viewModel.actionFlow,
        onEvent = { event ->
            when(event) {
                is NewVehiclePartEvent.OnBackClick ->
                    navController.popBackStack()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun NewVehiclePartScreen(
    navController: NavController,
    uiStateFlow: Flow<NewVehiclePartUiState>,
    onUiAction: Flow<NewVehiclePartUiAction>,
    onEvent: (NewVehiclePartEvent) -> Unit,
) {
    val uiState = uiStateFlow.collectAsStateWithLifecycle(initialValue = NewVehiclePartUiState())
    val context = LocalContext.current
    ObserveUiAction(
        flow = onUiAction
    ) {
        handleUiAction(
            context = context,
            navController = navController,
            uiAction = it,
            onEvent = onEvent
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { NewVehiclePartTopBar(
            isEditMode = uiState.value.isEditing,
            onBackClick = { onEvent(NewVehiclePartEvent.OnBackClick) }
        ) }
    ) { contentPadding ->
        NewVehiclePartContent(
            uiState = uiState.value,
            contentPadding = contentPadding,
            onEvent = onEvent
        )
    }
}

@Composable
fun NewVehiclePartContent(
    uiState: NewVehiclePartUiState,
    contentPadding: PaddingValues,
    onEvent: (NewVehiclePartEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(contentPadding)
    ) {
        NewVehiclePartForm(
            modifier = Modifier
                .weight(1f, fill = true)
                .verticalScroll(rememberScrollState()),
            state = uiState,
            onEvent = onEvent
        )

        Button(
            modifier = Modifier
                .padding(horizontal = SPACE_SCREEN_H, vertical = SPACING_LARGE_PLUS)
                .fillMaxWidth()
                .height(BIG_BUTTON_HEIGHT),
            enabled = !uiState.isLoading,
            onClick = { onEvent(NewVehiclePartEvent.OnSaveClick) },
            content = {
                Text(
                    text = stringResource(
                        id = if (uiState.isLoading) R.string.saving else R.string.save
                    ),
                    style = MaterialTheme.typography.titleMedium
                )
            },
        )
    }
}

@Composable
fun NewVehiclePartForm(
    modifier: Modifier,
    state: NewVehiclePartUiState,
    onEvent: (NewVehiclePartEvent) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = SPACE_SCREEN_H)
    ) {
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_LARGE_PLUS),
            value = state.partName.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnPartNameChange(it)) },
            label = stringResource(id = R.string.vehicle_part_name_title),
            isError = !state.partName.isValid(),
            supportingText = state.partName.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )
        DateSelectorView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.deploymentDate.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnDeploymentDateChange(it)) },
            label = stringResource(id = R.string.vehicle_deployment_date_title),
            isError = !state.deploymentDate.isValid(),
            supportingText = state.deploymentDate.errorMessage(LocalContext.current),
        )
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.deploymentKm.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnDeploymentKmChange(it)) },
            label = stringResource(id = R.string.vehicle_deployment_km_title),
            isError = !state.deploymentKm.isValid(),
            supportingText = state.deploymentKm.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
        )
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.lifeSpanInMonths.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnLifeSpanInMonthsChange(it)) },
            label = stringResource(id = R.string.vehicle_part_life_span_months_title),
            isError = !state.lifeSpanInMonths.isValid(),
            supportingText = state.lifeSpanInMonths.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
        )
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.lifeSpanInKm.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnLifeSpanInKmChange(it)) },
            label = stringResource(id = R.string.vehicle_part_life_span_km_title),
            isError = !state.lifeSpanInKm.isValid(),
            supportingText = state.lifeSpanInKm.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            )
        )
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.supplier.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnSupplierChange(it)) },
            label = stringResource(id = R.string.vehicle_part_supplier_title),
            isError = !state.supplier.isValid(),
            supportingText = state.supplier.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            )
        )
        InputFieldView(
            modifier = Modifier.fillMaxWidth()
                .padding(top = SPACING_SMALL),
            value = state.price.input,
            onValueChange = { onEvent(NewVehiclePartEvent.OnPriceChange(it)) },
            label = stringResource(id = R.string.vehicle_part_price_title),
            isError = !state.price.isValid(),
            supportingText = state.price.errorMessage(LocalContext.current),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewVehiclePartTopBar(
    isEditMode: Boolean,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(
            id = if (isEditMode) R.string.edit_vehicle_part_title else R.string.add_vehicle_part_title
        )) },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        }
    )
}

fun handleUiAction(
    context: Context,
    navController: NavController,
    uiAction: NewVehiclePartUiAction,
    onEvent: (NewVehiclePartEvent) -> Unit
) {
    when(uiAction) {
        is NewVehiclePartUiAction.ValidationCarKm -> {
            showUpdateVehicleKmDialog(
                context = context,
                updateVehicleKm = { onEvent(NewVehiclePartEvent.OnUpdateVehicleKmClick) }
            )
        }
        is NewVehiclePartUiAction.ShowSuccess -> {
            Toast.makeText(context, R.string.vehicle_part_added_successfully, Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        }
        is NewVehiclePartUiAction.ShowError -> {
            handleUiError(
                context = context,
                throwable = uiAction.throwable
            )
        }
    }
}

fun showUpdateVehicleKmDialog(
    context: Context,
    updateVehicleKm: () -> Unit
) {
    AlertDialog.Builder(context)
        .setTitle(R.string.attention)
        .setMessage(R.string.vehicle_part_update_km_message)
        .setPositiveButton(R.string.yes) { _, _ -> updateVehicleKm() }
        .setNegativeButton(R.string.no, null)
        .show()
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun NewVehiclePartScreenPreview() {
    MaintenanceAlarmTheme {
        NewVehiclePartScreen(
            navController = NavController(LocalContext.current),
            uiStateFlow = flowOf(NewVehiclePartUiState(
                isLoading = true
            )),
            onUiAction = flowOf(),
            onEvent = {}
        )
    }
}
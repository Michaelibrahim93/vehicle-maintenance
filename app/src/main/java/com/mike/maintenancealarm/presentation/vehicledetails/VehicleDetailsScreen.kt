package com.mike.maintenancealarm.presentation.vehicledetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.presentation.core.DateFormats
import com.mike.maintenancealarm.presentation.main.Route
import com.mike.maintenancealarm.presentation.updatevehiclekm.UpdateVehicleKmScreenComposable
import com.mike.maintenancealarm.presentation.vehicledetails.items.ItemEmptyParts
import com.mike.maintenancealarm.presentation.vehicledetails.items.ItemVehicle
import com.mike.maintenancealarm.presentation.vehicledetails.items.ItemVehiclePart
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("ContextCastToActivity")
@Composable
fun VehicleDetailsScreenComposable(
    navController: NavController,
    viewModel: VehicleDetailsViewModel = hiltViewModel()
) {
    VehicleDetailsScreen(
        fVehicleDetailsState = viewModel.fVehicleDetailsState,
        onEvent = { event ->
            Timber.tag("VehicleDetailsScreen").d("event: $event")
            when(event) {
                is VehicleDetailsEvents.OnBackClick -> navController.popBackStack()
                VehicleDetailsEvents.AddNewPart ->
                    navController.navigate(route = Route.NewVehiclePart(viewModel.vehicleId, null))
                is VehicleDetailsEvents.RenewPart ->
                    navController.navigate(route = Route.NewVehiclePart(viewModel.vehicleId, event.part.id))
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun VehicleDetailsScreen(
    fVehicleDetailsState: Flow<VehicleDetailsState>,
    onEvent: (VehicleDetailsEvents) -> Unit
) {
    val uiState = fVehicleDetailsState.collectAsStateWithLifecycle(initialValue = VehicleDetailsState())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { VehicleDetailsTopBar(onEvent) }
    ) { contentPadding ->
        VehicleDetailsScreenContent(
            contentPadding = contentPadding,
            fVehicleDetailsState = fVehicleDetailsState,
            onEvent = onEvent
        )
        if (uiState.value.showUpdateKmDialog) {
            UpdateVehicleKmScreenComposable(uiState.value.vehicle?.id!!) {
                onEvent(VehicleDetailsEvents.DismissUpdateKmDialog)
            }
        }
    }
}

@Composable
fun VehicleDetailsScreenContent(
    contentPadding: PaddingValues,
    fVehicleDetailsState: Flow<VehicleDetailsState>,
    onEvent: (VehicleDetailsEvents) -> Unit
) {
    val state = fVehicleDetailsState.collectAsStateWithLifecycle(initialValue = VehicleDetailsState())
    val dateFormat = remember { SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(contentPadding)
    ) {
        itemsIndexed(
            items = state.value.displayList,
        ) { index, item ->
            when (item) {
                is DetailsItem.VehicleItem -> ItemVehicle(
                    item = item,
                    dateFormat = dateFormat,
                    onUpdateVehicleKm = { onEvent(VehicleDetailsEvents.UpdateVehicleKm(item.vehicle)) }
                )
                is DetailsItem.PartItem -> ItemVehiclePart(
                    item = item,
                    dateFormat = dateFormat,
                    onRenewPart = { onEvent(VehicleDetailsEvents.RenewPart(item.part)) },
                )
                is DetailsItem.NoAddedParts -> ItemEmptyParts()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsTopBar(onEvent: (VehicleDetailsEvents) -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.vehicle_details_title)) },
        navigationIcon = {
            IconButton(onClick = {
                onEvent(VehicleDetailsEvents.OnBackClick)
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        actions = {
            IconButton (onClick = { onEvent(VehicleDetailsEvents.AddNewPart) }) {
                Icon(Icons.Default.AddCircle, contentDescription = "Add Vehicle Part")
            }
        }
    )
}
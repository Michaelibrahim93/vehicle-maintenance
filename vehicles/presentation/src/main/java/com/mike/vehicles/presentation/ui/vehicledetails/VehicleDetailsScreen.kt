package com.mike.vehicles.presentation.ui.vehicledetails

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.core.domain.utils.DateFormats
import com.mike.core.presentation.navigation.Route
import com.mike.core.presentation.utils.compose.DeviceType
import com.mike.core.presentation.utils.compose.LogCurrentScreen
import com.mike.core.presentation.utils.compose.OrientationType
import com.mike.core.presentation.utils.compose.rememberDeviceInfo
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import com.mike.resources.R
import com.mike.vehicles.presentation.ui.updatevehiclekm.UpdateVehicleKmScreenComposable
import com.mike.vehicles.presentation.ui.vehicledetails.items.ItemEmptyParts
import com.mike.vehicles.presentation.ui.vehicledetails.items.ItemVehicle
import com.mike.vehicles.presentation.ui.vehicledetails.items.ItemVehiclePart

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
                is VehicleDetailsEvents.OnBackClick ->
                    navController.popBackStack()
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
    val uiState by fVehicleDetailsState.collectAsStateWithLifecycle(initialValue = VehicleDetailsState())

    LogCurrentScreen("VehicleDetailsScreen")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { VehicleDetailsTopBar(onEvent) }
    ) { contentPadding ->
        VehicleDetailsScreenContent(
            contentPadding = contentPadding,
            fVehicleDetailsState = fVehicleDetailsState,
            onEvent = onEvent
        )
        if (uiState.showUpdateKmDialog) {
            UpdateVehicleKmScreenComposable(uiState.vehicle?.id!!) {
                onEvent(VehicleDetailsEvents.DismissUpdateKmDialog)
            }
        }
    }
}

@Composable
fun VehicleDetailsScreenContent(
    contentPadding: PaddingValues,
    fVehicleDetailsState: Flow<VehicleDetailsState>,
    onEvent: (VehicleDetailsEvents) -> Unit,
) {
    val state = fVehicleDetailsState.collectAsStateWithLifecycle(initialValue = VehicleDetailsState()).value
    val dateFormat = remember { SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault()) }
    val deviceInfo = rememberDeviceInfo()
    if (deviceInfo.orientation == OrientationType.LANDSCAPE || deviceInfo.deviceType == DeviceType.DESKTOP) {
        VehicleDetailsContentLandscapeColumn(
            state = state,
            dateFormat = dateFormat,
            contentPadding = contentPadding,
            onEvent = onEvent
        )
    } else {
        VehicleDetailsContentAllColumn(
            state = state,
            dateFormat = dateFormat,
            contentPadding = contentPadding,
            onEvent = onEvent
        )
    }
}

@Composable
fun VehicleDetailsContentLandscapeColumn(
    state: VehicleDetailsState,
    dateFormat: SimpleDateFormat,
    contentPadding: PaddingValues,
    onEvent: (VehicleDetailsEvents) -> Unit,
) {
    val vehicle = state.vehicle
    if (vehicle == null) return

    Row(
        modifier = Modifier.padding(contentPadding)
    ){
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ItemVehicle(
                item = DetailsItem.VehicleItem(vehicle),
                dateFormat = dateFormat,
                onUpdateVehicleKm = {
                    onEvent(VehicleDetailsEvents.UpdateVehicleKm(vehicle))
                }
            )
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ScrollableItems(
                contentPadding = PaddingValues(start = 0.dp, end = 0.dp),
                items = state.displayList.filter { it !is DetailsItem.VehicleItem },
                dateFormat = dateFormat,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun VehicleDetailsContentAllColumn(
    state: VehicleDetailsState,
    dateFormat: SimpleDateFormat,
    contentPadding: PaddingValues,
    onEvent: (VehicleDetailsEvents) -> Unit,
) {
    val vehicle = state.vehicle
    if (vehicle == null) return

    ScrollableItems(
        contentPadding = contentPadding,
        items = state.displayList,
        dateFormat = dateFormat,
        onEvent = onEvent
    )
}

@Composable
fun ScrollableItems(
    contentPadding: PaddingValues,
    items: List<DetailsItem>,
    dateFormat: SimpleDateFormat,
    onEvent: (VehicleDetailsEvents) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(contentPadding)
    ) {
        itemsIndexed(
            items = items,
        ) { index, item ->
            when (item) {
                is DetailsItem.VehicleItem -> ItemVehicle(
                    item = item,
                    dateFormat = dateFormat,
                    onUpdateVehicleKm = {
                        onEvent(VehicleDetailsEvents.UpdateVehicleKm(item.vehicle))
                    }
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
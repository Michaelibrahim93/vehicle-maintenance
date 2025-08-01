package com.mike.maintenancealarm.presentaion.vehicleslist

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mike.maintenancealarm.data.vo.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import timber.log.Timber
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.presentaion.core.DateFormats
import com.mike.maintenancealarm.utils.stringRes
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data object DestinationVehicleListScreen

data class VehicleListState(
    val vehicles: List<Vehicle> = emptyList()
)

@Composable
fun VehicleListComposable(
    navController: NavController,
    viewModel: VehicleListViewModel = hiltViewModel()
) {
    val dateFormat = remember {
        SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault())
    }
    VehicleListScreen(
        navController = navController,
        fState = viewModel.state,
        dateFormat = dateFormat,
        onEvent = { vehicleListEvent ->
            when (vehicleListEvent) {
                is VehicleListEvent.NavigateToVehicleDetails -> {
                    Timber.d("Navigate to vehicle details")
                }
                is VehicleListEvent.AddNewVehicle -> {
                    viewModel.insertVehicle()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavController,
    fState: Flow<VehicleListState>,
    dateFormat: SimpleDateFormat,
    onEvent: (VehicleListEvent) -> Unit
) {
    val state: VehicleListState by fState.collectAsStateWithLifecycle(initialValue = VehicleListState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(R.string.my_vehicles.stringRes()) },
                actions = {
                    IconButton (onClick = { onEvent(VehicleListEvent.AddNewVehicle) }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Add Vehicle")
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(contentPadding)
        ) {
            itemsIndexed(state.vehicles) { index, vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    isFirsItem = index == 0,
                    dateFormat = dateFormat,
                    onItemClick = {
                        onEvent(VehicleListEvent.NavigateToVehicleDetails(vehicle))
                    }
                )
            }
        }
    }
}

sealed class VehicleListEvent {
    data class NavigateToVehicleDetails(val vehicle: Vehicle) : VehicleListEvent()
    data object AddNewVehicle : VehicleListEvent()
}

@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun VehicleListScreenPreview() {
    VehicleListScreen(
        navController = NavController(LocalContext.current),
        fState = MutableStateFlow(VehicleListState(
            vehicles = listOf(
                Vehicle(
                    id = null,
                    vehicleImage = null,
                    vehicleName = "Vehicle 1",
                    currentKM = 1000.0,
                    lastKmUpdate = Date(),
                    vehicleStatus = VehicleStatus.OK
                ),
            )
        )),
        dateFormat = SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault()),
        onEvent = {}
    )
}
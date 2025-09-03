package com.mike.maintenancealarm.presentation.vehicledetails.items

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.domain.models.Vehicle
import com.mike.maintenancealarm.domain.models.VehicleStatus
import com.mike.maintenancealarm.presentation.core.partStatusColor
import com.mike.maintenancealarm.presentation.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentation.theme.SPACING_EXTRA_LARGE
import com.mike.maintenancealarm.presentation.theme.SPACING_LARGE
import com.mike.maintenancealarm.presentation.theme.SPACING_LARGE_PLUS
import com.mike.maintenancealarm.presentation.theme.SPACING_MEDIUM
import com.mike.maintenancealarm.presentation.theme.SPACING_SMALL
import com.mike.maintenancealarm.presentation.vehicledetails.DetailsItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun ItemVehicle(
    item: DetailsItem.VehicleItem,
    dateFormat: SimpleDateFormat,
    onUpdateVehicleKm: (DetailsItem.VehicleItem) -> Unit
) {
    val vehicle = item.vehicle
    Column (
        modifier = Modifier.padding(
            start = SPACING_MEDIUM,
            bottom = SPACING_EXTRA_LARGE
        )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = vehicle.vehicleImage,
            contentDescription = vehicle.vehicleName,
            placeholder = painterResource(R.drawable.ic_car_placeholder),
            error = painterResource(R.drawable.ic_car_placeholder),
            modifier = Modifier
                .padding(top = SPACING_LARGE_PLUS)
                .width(100.dp)
                .height(100.dp)
                .clip(CircleShape)
                .border(2.dp, colorResource(R.color.image_foreground), CircleShape)
                .padding(6.dp)
        )

        Text(
            text = vehicle.vehicleName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = SPACING_LARGE)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.vehicle_km_x, vehicle.currentKM.roundToInt().toString()),
                style = MaterialTheme.typography.bodyLarge,
            )
            IconButton(onClick = {
                onUpdateVehicleKm(item)
            }) {
                Icon(
                    painter = painterResource(R.drawable.outline_add_road_24),
                    contentDescription = "Add KM",
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        Text(
            text = when(vehicle.vehicleStatus) {
                VehicleStatus.OK -> stringResource(R.string.vehicle_status_ok)
                VehicleStatus.HAS_NEAR_EXPIRATION -> stringResource(R.string.vehicle_status_near_expiration)
                VehicleStatus.HAS_EXPIRED -> stringResource(R.string.vehicle_status_expired)
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = partStatusColor(vehicle.vehicleStatus)
            ),
            modifier = Modifier.padding(top = SPACING_SMALL)
        )
        Text(
            text = stringResource(
                R.string.vehicle_last_km_update,
                dateFormat.format(vehicle.lastKmUpdate)
            ),
            style = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .padding(top = SPACING_SMALL)
        )
    }
}

@Preview(
    name = "ItemVehicle Preview",
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ItemVehiclePreview() {
    MaintenanceAlarmTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Text(
                    text = stringResource(R.string.vehicle_details_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(SPACING_EXTRA_LARGE)
                )
            }
        ) { contentPadding ->
            Box(
                modifier = Modifier.padding(contentPadding)
            ){
                ItemVehicle(
                    item = DetailsItem.VehicleItem(
                        vehicle = Vehicle(
                            id = null,
                            vehicleName = "Toyota Corolla",
                            vehicleImage = "https://example.com/car_image.png",
                            currentKM = 15000.0,
                            lastKmUpdate = Date(),
                            vehicleStatus = VehicleStatus.OK,
                        )
                    ),
                    dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
                    onUpdateVehicleKm = {}
                )
            }
        }
    }
}
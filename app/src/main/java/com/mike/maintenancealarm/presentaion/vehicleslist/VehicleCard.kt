package com.mike.maintenancealarm.presentaion.vehicleslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.presentaion.core.DateFormats
import com.mike.maintenancealarm.presentaion.core.partStatusColor
import com.mike.maintenancealarm.presentaion.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentaion.theme.SPACE_SCREEN_H
import com.mike.maintenancealarm.presentaion.theme.SPACING_LARGE
import com.mike.maintenancealarm.presentaion.theme.SPACING_MEDIUM
import com.mike.maintenancealarm.presentaion.theme.SPACING_SMALL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    isFirsItem: Boolean,
    dateFormat: SimpleDateFormat,
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = SPACE_SCREEN_H,
                end = SPACE_SCREEN_H,
                top = if (isFirsItem) SPACING_LARGE else 0.dp,
                bottom = SPACING_LARGE
            )
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .clickable { onItemClick() },
            shape = RoundedCornerShape(6.dp),
        ) {
            CardContent(
                vehicle = vehicle,
                dateFormat = dateFormat
            )
        }
    }
}

@Composable
fun CardContent(
    vehicle: Vehicle,
    dateFormat: SimpleDateFormat
) {
    Row(
        modifier = Modifier.padding(all= SPACING_LARGE)
    ) {
        AsyncImage(
            model = vehicle.vehicleImage,
            contentDescription = vehicle.vehicleName,
            placeholder = painterResource(R.drawable.ic_car_placeholder),
            error = painterResource(R.drawable.ic_car_placeholder),
            modifier = Modifier.width(50.dp)
                .height(50.dp)
                .padding(4.dp)
        )

        Column (
            modifier = Modifier.padding(start = SPACING_MEDIUM)
                .fillMaxWidth()
        ) {
            Text(
                text = vehicle.vehicleName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.vehicle_km, vehicle.currentKM.roundToInt().toString()),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = SPACING_SMALL)
            )
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
                    .align(Alignment.End)
            )
        }
    }
}

@Preview
@Composable
fun VehicleCardPreview() {
    MaintenanceAlarmTheme {
        VehicleCard(
            vehicle = Vehicle(
                id = null,
                vehicleImage = null,
                vehicleName = "Vehicle 1",
                currentKM = 1000.0,
                lastKmUpdate = Date(),
                vehicleStatus = VehicleStatus.HAS_NEAR_EXPIRATION
            ),
            isFirsItem = true,
            dateFormat = SimpleDateFormat(DateFormats.DAY_FORMAT, Locale.getDefault()),
            onItemClick = {}
        )
    }
}
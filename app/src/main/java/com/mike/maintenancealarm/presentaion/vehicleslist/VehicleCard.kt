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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mike.maintenancealarm.R
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.presentaion.theme.MaintenanceAlarmTheme
import com.mike.maintenancealarm.presentaion.theme.SPACE_SCREEN_H
import com.mike.maintenancealarm.presentaion.theme.SPACING_LARGE
import com.mike.maintenancealarm.presentaion.theme.SPACING_MEDIUM
import java.util.Date

@Composable
fun VehicleCard(
    vehicle: Vehicle,
    isFirsItem: Boolean,
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
            shape = RoundedCornerShape(6.dp)
        ) {
            CardContent(vehicle = vehicle)
        }
    }
}

@Composable
fun CardContent(vehicle: Vehicle) {
    Row(
        modifier = Modifier.padding(all= SPACING_MEDIUM)
    ) {
        AsyncImage(
            model = vehicle.vehicleImage,
            contentDescription = vehicle.vehicleName,
            placeholder = painterResource(R.drawable.ic_car_placeholder),
            modifier = Modifier.width(50.dp)
                .height(50.dp)
        )

        Column (
            modifier = Modifier.padding(start = SPACING_MEDIUM)
                .fillMaxWidth()
        ) {
            Text(text = vehicle.vehicleName)
            Text(text = "Current KM: ${vehicle.currentKM}")
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
                vehicleStatus = VehicleStatus.OK
            ),
            isFirsItem = true,
            onItemClick = {}
        )
    }
}
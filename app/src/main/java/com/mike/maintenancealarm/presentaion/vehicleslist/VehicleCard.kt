package com.mike.maintenancealarm.presentaion.vehicleslist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.presentaion.theme.MaintenanceAlarmTheme
import java.util.Date

@Composable
fun VehicleCard(
    vehicle: Vehicle
) {

}

@Preview
@Composable
fun VehicleCardPreview() {
    MaintenanceAlarmTheme {
        VehicleCard(
            vehicle = Vehicle(
                id = null,
                vehicleName = "Vehicle 1",
                currentKM = 1000.0,
                lastKmUpdate = Date(),
                vehicleStatus = VehicleStatus.OK
            )
        )
    }
}
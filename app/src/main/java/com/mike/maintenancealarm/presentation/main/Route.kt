package com.mike.maintenancealarm.presentation.main

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    object Splash: Route()
    @Serializable
    object VehicleList: Route()
    @Serializable
    object NewVehicle: Route()
    @Serializable
    data class VehicleDetails(val vehicleId: Long): Route()
    @Serializable
    data class NewVehiclePart(
        val vehicleId: Long,
        val vehiclePartId: Long?
    ): Route()
}
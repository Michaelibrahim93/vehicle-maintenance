package com.mike.maintenancealarm.data.vo

import com.mike.maintenancealarm.domain.models.LifeSpan
import com.mike.maintenancealarm.domain.models.VehiclePartStatus
import com.mike.maintenancealarm.modelFactory.TestVehiclePartFactory
import org.junit.jupiter.api.Test
import java.util.Calendar
import java.util.Date

class VehiclePartStatusTest {
    // KM OK - Date OK - out OK
    @Test
    fun `partStatus KM OK Date OK out OK`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 3000.0
        )

        assert(partStatus == VehiclePartStatus.OK) {
            "Expected part status to be OK, but was $partStatus"
        }
    }

    // KM NEAR_EXPIRY - Date OK - out NEAR_EXPIRY
    @Test
    fun `partStatus KM NEAR_EXPIRY Date OK out NEAR_EXPIRY`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 5550.0
        )

        assert(partStatus == VehiclePartStatus.NEAR_EXPIRATION) {
            "Expected part status to be NEAR_EXPIRATION, but was $partStatus"
        }
    }

    // KM OK - Date NEAR_EXPIRY - out NEAR_EXPIRY
    @Test
    fun `partStatus KM OK Date NEAR_EXPIRY out NEAR_EXPIRY`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -5)
                add(Calendar.DAY_OF_MONTH, -20)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 3000.0,
        )

        assert(partStatus == VehiclePartStatus.NEAR_EXPIRATION) {
            "Expected part status to be NEAR_EXPIRATION, but was $partStatus"
        }
    }

    // KM NEAR_EXPIRY - Date NEAR_EXPIRY - out NEAR_EXPIRY
    @Test
    fun `partStatus KM NEAR_EXPIRY Date NEAR_EXPIRY out NEAR_EXPIRY`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -5)
                add(Calendar.DAY_OF_MONTH, -20)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 5550.0
        )

        assert(partStatus == VehiclePartStatus.NEAR_EXPIRATION) {
            "Expected part status to be NEAR_EXPIRATION, but was $partStatus"
        }
    }

    // KM EXPIRED - Date OK - out EXPIRED
    @Test
    fun `partStatus KM EXPIRED Date OK out EXPIRED`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 6000.0
        )

        assert(partStatus == VehiclePartStatus.EXPIRED) {
            "Expected part status to be EXPIRED, but was $partStatus"
        }
    }

    // KM EXPIRED - Date NEAR_EXPIRY - out EXPIRED
    @Test
    fun `partStatus KM EXPIRED Date NEAR_EXPIRY out EXPIRED`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -5)
                add(Calendar.DAY_OF_MONTH, -20)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 6000.0
        )

        assert(partStatus == VehiclePartStatus.EXPIRED) {
            "Expected part status to be EXPIRED, but was $partStatus"
        }
    }

    // KM OK - Date EXPIRED - out EXPIRED
    @Test
    fun `partStatus KM OK Date EXPIRED out EXPIRED`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -7)
                add(Calendar.DAY_OF_MONTH, -10)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 3000.0
        )

        assert(partStatus == VehiclePartStatus.EXPIRED) {
            "Expected part status to be EXPIRED, but was $partStatus"
        }
    }

    // KM NEAR_EXPIRY - Date EXPIRED - out EXPIRED
    @Test
    fun `partStatus KM NEAR_EXPIRY Date EXPIRED out EXPIRED`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -7)
                add(Calendar.DAY_OF_MONTH, -10)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 5550.0
        )

        assert(partStatus == VehiclePartStatus.EXPIRED) {
            "Expected part status to be EXPIRED, but was $partStatus"
        }
    }

    // KM EXPIRED - Date EXPIRED - out EXPIRED
    @Test
    fun `partStatus KM EXPIRED Date EXPIRED out EXPIRED`() {
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            deploymentKM = 1000.0,
            deploymentDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -7)
                add(Calendar.DAY_OF_MONTH, -10)
            }.time,
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )

        val partStatus = VehiclePartStatus.partStatus(
            part = vehiclePart,
            currentVehicleKm = 6030.0
        )

        assert(partStatus == VehiclePartStatus.EXPIRED) {
            "Expected part status to be EXPIRED, but was $partStatus"
        }
    }
}
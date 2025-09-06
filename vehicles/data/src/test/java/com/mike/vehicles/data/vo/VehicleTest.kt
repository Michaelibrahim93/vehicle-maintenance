package com.mike.vehicles.data.vo

import com.mike.domian.vehicles.models.LifeSpan
import com.mike.domian.vehicles.models.VehicleStatus
import com.mike.vehicles.testutils.TestVehicleFactory
import com.mike.vehicles.testutils.TestVehiclePartFactory
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.util.Date

class VehicleTest {
    private val vehicleId = 100L

    //Vehicle OK - Part OK - Vehicle OK
    @Test
    fun `updateStatus Vehicle OK and 1 Part OK out Vehicle OK`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
            )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 3000.0,
            vehicleStatus = VehicleStatus.OK
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.OK) {
            "Expected vehicle status to be OK, but was ${updatedVehicle.vehicleStatus}"
        }
    }

    //Vehicle OK - Part NEAR_EXPIRY - Vehicle NEAR_EXPIRY
    @Test
    fun `updateStatus Vehicle OK and 1 Part Near expiry out Vehicle Near expiry`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 5550.0,
            vehicleStatus = VehicleStatus.OK
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_NEAR_EXPIRATION) {
            "Expected vehicle status to be NEAR_EXPIRY, but was ${updatedVehicle.vehicleStatus}"
        }

    }
    //Vehicle OK - Part EXPIRED - Vehicle EXPIRED
    @Test
    fun `updateStatus Vehicle OK and 1 Part expired out vehicle expired`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 6000.0,
            vehicleStatus = VehicleStatus.OK
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_EXPIRED) {
            "Expected vehicle status to be HAS_EXPIRED, but was ${updatedVehicle.vehicleStatus}"
        }
    }

    //Vehicle NEAR_EXPIRY - Part OK - Vehicle NEAR_EXPIRY
    @Test
    fun `updateStatus Vehicle NEAR_EXPIRY and 1 Part OK out vehicle NEAR_EXPIRY`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 1500.0,
            vehicleStatus = VehicleStatus.HAS_NEAR_EXPIRATION
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_NEAR_EXPIRATION) {
            "Expected vehicle status to be NEAR_EXPIRY, but was ${updatedVehicle.vehicleStatus}"
        }
    }
    //Vehicle NEAR_EXPIRY - Part NEAR_EXPIRY - Vehicle NEAR_EXPIRY
    @Test
    fun `updateStatus Vehicle NEAR_EXPIRY and 1 Part NEAR_EXPIRY out vehicle NEAR_EXPIRY`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 5550.0,
            vehicleStatus = VehicleStatus.HAS_NEAR_EXPIRATION
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_NEAR_EXPIRATION) {
            "Expected vehicle status to be HAS_NEAR_EXPIRATION, but was ${updatedVehicle.vehicleStatus}"
        }
    }
    //Vehicle NEAR_EXPIRY - Part EXPIRED - Vehicle EXPIRED
    @Test
    fun `updateStatus Vehicle NEAR_EXPIRY and 1 Part EXPIRED out vehicle EXPIRED`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 6000.0,
            vehicleStatus = VehicleStatus.HAS_NEAR_EXPIRATION
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_EXPIRED) {
            "Expected vehicle status to be HAS_NEAR_EXPIRATION, but was ${updatedVehicle.vehicleStatus}"
        }
    }

    //Vehicle EXPIRED - Part OK - Vehicle EXPIRED
    @Test
    fun `updateStatus Vehicle HAS_EXPIRED and 1 Part OK out vehicle EXPIRED`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 1000.0,
            vehicleStatus = VehicleStatus.HAS_EXPIRED
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_EXPIRED) {
            "Expected vehicle status to be HAS_NEAR_EXPIRATION, but was ${updatedVehicle.vehicleStatus}"
        }
    }
    //Vehicle EXPIRED - Part NEAR_EXPIRY - Vehicle EXPIRED
    @Test
    fun `updateStatus Vehicle HAS_EXPIRED and 1 Part NEAR_EXPIRY out vehicle EXPIRED`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 5550.0,
            vehicleStatus = VehicleStatus.HAS_EXPIRED
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_EXPIRED) {
            "Expected vehicle status to be HAS_NEAR_EXPIRATION, but was ${updatedVehicle.vehicleStatus}"
        }
    }
    //Vehicle EXPIRED - Part EXPIRED - Vehicle EXPIRED
    @Test
    fun `updateStatus Vehicle HAS_EXPIRED and 1 Part EXPIRED out vehicle EXPIRED`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId,
            deploymentKM = 1000.0,
            deploymentDate = Date(),
            lifeSpan = LifeSpan(km = 5000.0, months = 6)
        )
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 6500.0,
            vehicleStatus = VehicleStatus.HAS_EXPIRED
        )

        // When updateStatus is called
        val updatedVehicle = vehicle.updateStatus(listOf(vehiclePart))

        // Then the vehicle status should remain OK
        assert(updatedVehicle.vehicleStatus == VehicleStatus.HAS_EXPIRED) {
            "Expected vehicle status to be HAS_NEAR_EXPIRATION, but was ${updatedVehicle.vehicleStatus}"
        }
    }
}
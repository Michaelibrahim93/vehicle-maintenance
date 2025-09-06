package com.mike.domian.vehicles.usecases

import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.repos.VehiclesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

class AddVehicleUseCaseImplTest {
    private lateinit var addVehicleUseCaseImpl: AddVehicleUseCaseImpl
    private lateinit var vehicleRepository: VehiclesRepository

    @BeforeEach
    fun setup() {
        vehicleRepository = mockk()
        addVehicleUseCaseImpl = AddVehicleUseCaseImpl(vehicleRepository)

    }

    @Test
    @DisplayName("Test insertVehicle is called")
    fun testInsertVehicleIsCalled() = runTest {
        // Given
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } returns Unit
        // When
        addVehicleUseCaseImpl.execute(vehicle)
        // Then
        coVerify { vehicleRepository.insertVehicle(vehicle) }
    }

    @Test
    @DisplayName("Test insertVehicle succeed if insertVehicle is called")
    fun testInsertVehicleSucceed() = runTest {
        // Given
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } returns Unit
        // When
        val result = runCatching {
            addVehicleUseCaseImpl.execute(vehicle)
        }
        // Then
        assert(result.isSuccess)
    }
}
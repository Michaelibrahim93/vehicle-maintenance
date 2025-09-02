package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.domain.repos.VehiclePartsRepository
import com.mike.maintenancealarm.domain.repos.VehiclesRepository
import com.mike.maintenancealarm.domain.vo.Vehicle
import com.mike.maintenancealarm.modelFactory.TestVehicleFactory
import com.mike.maintenancealarm.modelFactory.TestVehiclePartFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.Calendar

class UpdateVehicleUseCaseImplTest {
    private lateinit var vehiclesRepository: VehiclesRepository
    private lateinit var vehiclePartsRepository: VehiclePartsRepository
    private lateinit var updateVehicleUseCase: UpdateVehicleUseCase

    @BeforeEach
    fun setUp() {
        vehiclesRepository = mockk()
        vehiclePartsRepository = mockk()
        updateVehicleUseCase = UpdateVehicleUseCaseImpl(vehiclesRepository, vehiclePartsRepository)
    }

    @Tag("executeUpdateKm")
    @Test
    @DisplayName("executeUpdateKm loadVehicle is called")
    fun testExecuteUpdateKmLoadVehicleIsCalled() = runTest {
        // Given
        val vehicleId = 2L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId, currentKM = 100.0)
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclePartsRepository.loadVehicleParts(vehicleId) } returns
                TestVehiclePartFactory.generateVehicleParts(
                    count = 1,
                    deploymentKMList = listOf(100.0)
                )
        coEvery { vehiclesRepository.updateVehicle(any()) } returns Unit

        // When
        updateVehicleUseCase.executeUpdateKm(vehicleId, 200.0)

        // Then
        coVerify { vehiclesRepository.loadVehicle(vehicleId) }
    }

    @Tag("executeUpdateKm")
    @Test
    @DisplayName("executeUpdateKm updateVehicle is called with updated KM")
    fun executeUpdateKmUpdateVehicleWithUpdatedKm() = runTest {
        // Given
        val slot = slot<Vehicle>()
        val vehicleId = 2L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId, currentKM = 100.0)
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclePartsRepository.loadVehicleParts(vehicleId) } returns
                TestVehiclePartFactory.generateVehicleParts(
                    count = 1,
                    deploymentKMList = listOf(100.0)
                )
        coEvery { vehiclesRepository.updateVehicle(capture(slot)) } returns Unit
        // When
        updateVehicleUseCase.executeUpdateKm(vehicleId, 200.0)
        // Then
        assert(slot.captured.currentKM == 200.0)
    }

    @Tag("executeUpdateKm")
    @Test
    @DisplayName("executeUpdateKm updateVehicle is called with updated date")
    fun executeUpdateKmUpdateVehicleWithUpdatedDate() = runTest {
        // Given
        val slot = slot<Vehicle>()
        val vehicleId = 2L
        val lastKmDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -2)
        }.time
        val vehicle = TestVehicleFactory.generateVehicle(
            id = vehicleId,
            currentKM = 100.0,
            lastKmUpdate = lastKmDate
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclePartsRepository.loadVehicleParts(vehicleId) } returns
                TestVehiclePartFactory.generateVehicleParts(
                    count = 1,
                    deploymentKMList = listOf(100.0)
                )
        coEvery { vehiclesRepository.updateVehicle(capture(slot)) } returns Unit
        // When
        updateVehicleUseCase.executeUpdateKm(vehicleId, 200.0)
        // Then
        assert(slot.captured.lastKmUpdate.time != lastKmDate.time)
    }
}
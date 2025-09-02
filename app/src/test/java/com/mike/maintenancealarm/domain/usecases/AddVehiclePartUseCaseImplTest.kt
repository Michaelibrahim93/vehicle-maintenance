package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.domain.repos.VehiclePartsRepository
import com.mike.maintenancealarm.domain.repos.VehiclesRepository
import com.mike.maintenancealarm.domain.vo.errors.VehicleError
import com.mike.maintenancealarm.domain.vo.errors.VehicleErrorFactory
import com.mike.maintenancealarm.modelFactory.TestVehicleFactory
import com.mike.maintenancealarm.modelFactory.TestVehiclePartFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test

class AddVehiclePartUseCaseImplTest {
    private lateinit var vehiclesRepository: VehiclesRepository
    private lateinit var vehiclePartsRepository: VehiclePartsRepository
    private lateinit var addVehiclePartUseCase: AddVehiclePartUseCase

    @BeforeEach
    fun setUp() {
        vehiclesRepository = mockk()
        vehiclePartsRepository = mockk()
        addVehiclePartUseCase = AddVehiclePartUseCaseImpl(vehiclesRepository, vehiclePartsRepository)
    }

    @Test
    @DisplayName("execute loadVehicle is called")
    fun testExecuteLoadVehicleIsCalled() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 0,
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        addVehiclePartUseCase.execute(vehiclePart)
        // Then
        coVerify { vehiclesRepository.loadVehicle(vehicleId) }
    }

    @Test
    @DisplayName("execute insertVehiclePart is called when vehiclePart is new")
    fun testExecuteInsertVehiclePartIsCalled() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 0, // new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        addVehiclePartUseCase.execute(vehiclePart)
        // Then
        coVerify { vehiclePartsRepository.insertVehiclePart(vehiclePart) }
    }

    @Test
    @DisplayName("execute updateVehiclePart is not called when vehiclePart is new")
    fun testExecuteUpdateVehiclePartIsNotCalled() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 0,// new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        addVehiclePartUseCase.execute(vehiclePart)
        // Then
        coVerify(exactly = 0) { vehiclePartsRepository.updateVehiclePart(vehiclePart) }
    }

    @Test
    @DisplayName("execute updateVehiclePart is called when vehiclePart is not new")
    fun testExecuteUpdateVehiclePartIsCalled() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 1,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        addVehiclePartUseCase.execute(vehiclePart)
        // Then
        coVerify(exactly = 1) { vehiclePartsRepository.updateVehiclePart(vehiclePart) }
    }

    @Test
    @DisplayName("execute insertVehiclePart is not called when vehiclePart is not new")
    fun testExecuteInsertVehiclePartNotCalled() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 1,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        addVehiclePartUseCase.execute(vehiclePart)
        // Then
        coVerify(exactly = 0) { vehiclePartsRepository.insertVehiclePart(vehiclePart) }
    }

    //throws

    @Test
    @DisplayName("execute throws when loadVehicle throws")
    fun testExecuteThrowsLoadVehicleThrows() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 1,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } throws
                VehicleErrorFactory.vehicleNotFoundError(Throwable("Vehicle not found"))
        coEvery { vehiclesRepository.updateVehicle(vehicle) } returns Unit
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        val resultException = runCatching { addVehiclePartUseCase.execute(vehiclePart) }.exceptionOrNull()
        // Then
        assert(resultException is VehicleError.LocalDbError)
    }

    @Test
    @DisplayName("execute throws updateVehicle throws")
    fun testExecuteThrowsWhenUpdateVehicleThrows() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 0,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) } throws
                VehicleErrorFactory.vehicleNotFoundError(Throwable("Vehicle not found"))
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } returns Unit
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        val resultException = runCatching { addVehiclePartUseCase.execute(vehiclePart) }.exceptionOrNull()
        // Then
        assert(resultException is VehicleError.LocalDbError)
    }

    @Test
    @DisplayName("execute throws insertVehiclePart throws")
    fun testExecuteThrowsWhenInsertVehiclePartThrows() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 0,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) }
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) } throws
                VehicleErrorFactory.vehicleNotFoundError(Throwable("Vehicle not found"))
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } returns Unit
        // When
        val resultException = runCatching { addVehiclePartUseCase.execute(vehiclePart) }.exceptionOrNull()
        // Then
        assert(resultException is VehicleError.LocalDbError)
    }

    @Test
    @DisplayName("execute throws updateVehiclePart throws")
    fun testExecuteThrowsWhenUpdateVehiclePartThrows() = runTest {
        // Given
        val vehicleId = 1L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            id = 1,// not new vehicle
            vehicleId = vehicleId,
        )
        coEvery { vehiclesRepository.loadVehicle(vehicleId) } returns vehicle
        coEvery { vehiclesRepository.updateVehicle(vehicle) }
        coEvery { vehiclePartsRepository.insertVehiclePart(vehiclePart) }
        coEvery { vehiclePartsRepository.updateVehiclePart(vehiclePart) } throws
                VehicleErrorFactory.vehicleNotFoundError(Throwable("Vehicle not found"))
        // When
        val resultException = runCatching { addVehiclePartUseCase.execute(vehiclePart) }.exceptionOrNull()
        // Then
        assert(resultException is VehicleError.LocalDbError)
    }
}
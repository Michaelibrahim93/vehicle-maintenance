package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.errors.VehicleError
import com.mike.maintenancealarm.data.vo.errors.VehicleErrorFactory
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
        //test install
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } returns Unit
        // call use case
        addVehicleUseCaseImpl.execute(vehicle)
        // verify
        coVerify { vehicleRepository.insertVehicle(vehicle) }
    }

    @Test
    @DisplayName("Test insertVehicle succeed if insertVehicle is called")
    fun testInsertVehicleSucceed() = runTest {
        //test install
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } returns Unit
        // call
        val result = runCatching {
            addVehicleUseCaseImpl.execute(vehicle)
        }
        // verify
        assert(result.isSuccess)
    }

    @Test
    @DisplayName("Test insertVehicle throws LocalDbError execute throws LocalDbError")
    fun testInsertVehicleThrowsLocalDbError() = runTest {
        //test install
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } throws VehicleErrorFactory
            .vehicleNameExistsError(Throwable())
        // verify execute throws LocalDbError
        val result = runCatching {
            addVehicleUseCaseImpl.execute(vehicle)
        }
        assert(result.exceptionOrNull() is VehicleError.LocalDbError)
    }

    @Test
    @DisplayName("Test insertVehicle throws Throwable execute throws UnknownError")
    fun testInsertVehicleThrowsThrowableExecuteThrowsUnknownError() = runTest {
        //test install
        val vehicle = mockk<Vehicle>()
        coEvery { vehicleRepository.insertVehicle(vehicle) } throws RuntimeException("Message")
        // verify execute throws LocalDbError
        val result = runCatching {
            addVehicleUseCaseImpl.execute(vehicle)
        }
        assert(result.exceptionOrNull() is VehicleError.UnknownVehicleError)
    }
}
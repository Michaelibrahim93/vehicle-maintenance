package com.mike.maintenancealarm.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.VehicleStatus
import com.mike.maintenancealarm.data.vo.errors.LocalDbErrorKey
import com.mike.maintenancealarm.data.vo.errors.VehicleError
import com.mike.maintenancealarm.modelFactory.TestVehicleFactory
import com.mike.maintenancealarm.modelFactory.TestVehiclePartFactory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import timber.log.Timber
import kotlin.test.assertFailsWith

class AddVehiclePartUseCaseImplTest {
    private lateinit var vehiclesRepository: VehiclesRepository
    private lateinit var vehiclePartsRepository: VehiclePartsRepository
    private lateinit var useCase: AddVehiclePartUseCaseImpl

    private val vehicleId = 123L

    @Before
    fun setUp() {
        vehiclesRepository = mock()
        vehiclePartsRepository = mock()
        useCase = AddVehiclePartUseCaseImpl(vehiclesRepository, vehiclePartsRepository)
    }

    //check calls unit tests
    @Test
    fun `execute should call loadVehicle`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(vehicleId = vehicleId)
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(vehicle)

        // When execute is called
        useCase.execute(vehiclePart)
        Timber.d("execute should call loadVehicle")
        // Then insertVehiclePart and updateStatus should be called
        verify(vehiclesRepository).loadVehicle(vehicleId)
    }

    @Test
    fun `execute should call insertVehiclePart`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(vehicleId = vehicleId)
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(vehicle)

        // When execute is called
        useCase.execute(vehiclePart)
        Timber.d("execute should call loadVehicle")

        verify(vehiclePartsRepository).insertVehiclePart(vehiclePart)
    }

    @Test
    fun `execute should call insertVehicle`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(vehicleId = vehicleId)
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(vehicle)

        // When execute is called
        useCase.execute(vehiclePart)
        Timber.d("execute should call loadVehicle")

        verify(vehiclesRepository).insertVehicle(vehicle)
    }

    // Test cases for exceptions during insertVehiclePart
    @Test
    fun `execute should call no vehicle returned throws db NOT_FOUND thrown`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(vehicleId = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(null)

        // When execute is called
        val throwable = assertFailsWith<VehicleError.LocalDbError> {
            useCase.execute(vehiclePart)
        }

        assert(throwable.key == LocalDbErrorKey.NOT_FOUND)
    }

    @Test
    fun `execute insertVehiclePart throws SQLiteConstraintException then db NAME_EXISTS thrown`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId
        )
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(vehicle)
        `when`(vehiclePartsRepository.insertVehiclePart(vehiclePart)).thenThrow(SQLiteConstraintException())

        // When execute is called
        val throwable = assertFailsWith<VehicleError.LocalDbError> {
            useCase.execute(vehiclePart)
        }

        assert(throwable.key == LocalDbErrorKey.NAME_EXISTS)
    }

    @Test
    fun `execute insertVehiclePart throws generic then db UNKNOWN_ERROR thrown`() = runTest {
        // Given a VehiclePart with a valid vehicleId
        val vehiclePart = TestVehiclePartFactory.generateVehiclePart(
            vehicleId = vehicleId
        )
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)

        `when`(vehiclesRepository.loadVehicle(vehicleId)).thenReturn(vehicle)
        `when`(vehiclePartsRepository.insertVehiclePart(vehiclePart)).thenThrow(RuntimeException("Generic error"))


        // When execute is called
        val throwable = assertFailsWith<VehicleError.LocalDbError> {
            useCase.execute(vehiclePart)
        }

        assert(throwable.key == LocalDbErrorKey.UNKNOWN_ERROR)
    }
}
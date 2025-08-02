package com.mike.maintenancealarm.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.errors.LocalDbErrorKey
import com.mike.maintenancealarm.data.vo.errors.VehicleError
import com.mike.maintenancealarm.modelFactory.TestVehicleFactory
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.assertFailsWith


class AddVehicleUseCaseImplTest {
    private lateinit var useCase: AddVehicleUseCaseImpl
    private lateinit var repository: VehiclesRepository
    @Before
    fun setUp() {
        repository = mock()
        useCase = AddVehicleUseCaseImpl(repository)
    }

    @Test
    fun `execute should call insertVehicle on repository`() = runTest {
        val testVehicle = TestVehicleFactory.generateVehicle()
        useCase.execute(testVehicle)
        verify(repository).insertVehicle(testVehicle)
    }

    @Test
    fun `execute should throw vehicleNameExistsError on SQLiteConstraintException`() = runTest {
        val exception = SQLiteConstraintException("UNIQUE constraint failed")
        val testVehicle = TestVehicleFactory.generateVehicle()

        doThrow(exception).`when`(repository).insertVehicle(any())

        val throwable = assertFailsWith<VehicleError.LocalDbError> {
            useCase.execute(testVehicle)
        }

        assert(throwable.key == LocalDbErrorKey.VEHICLE_NAME_EXISTS)
    }

    @Test
    fun `execute should throw unknownError on generic exception`() = runTest {
        val exception = RuntimeException("Something went wrong")
        val testVehicle = TestVehicleFactory.generateVehicle()

        doThrow(exception).`when`(repository).insertVehicle(any())

        val throwable = assertFailsWith<VehicleError.LocalDbError> {
            useCase.execute(testVehicle)
        }

        assert(throwable.key == LocalDbErrorKey.UNKNOWN_ERROR)
    }
}
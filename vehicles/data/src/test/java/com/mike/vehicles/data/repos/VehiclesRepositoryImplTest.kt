package com.mike.vehicles.data.repos

import android.database.sqlite.SQLiteConstraintException
import com.mike.data.vo.errors.LocalDbErrorKey
import com.mike.data.vo.errors.VehicleError
import com.mike.vehicles.data.storage.db.dao.VehicleDao
import com.mike.vehicles.testutils.TestVehicleFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class VehiclesRepositoryImplTest {
    private lateinit var vehicleDao: VehicleDao
    private lateinit var vehiclesRepository: VehiclesRepositoryImpl

    @BeforeEach
    fun setUp() {
        vehicleDao = mockk()
        vehiclesRepository = VehiclesRepositoryImpl(vehicleDao)
    }

    @Tag("insertVehicle")
    @Test
    @DisplayName("insertVehicle should call vehicleDao insertVehicle")
    fun insertVehicleShouldCallVehicleDaoInsertVehicle() = runTest {
        // Given
        val vehicle = TestVehicleFactory.generateVehicle()
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.insertVehicle(vehicleEntity) } returns Unit
        // When
        vehiclesRepository.insertVehicle(vehicle)
        // Then
        coVerify { vehicleDao.insertVehicle(vehicleEntity) }
    }

    @Tag("insertVehicle")
    @Test
    @DisplayName("insertVehicle throws LocalDbError when vehicleDao throws SQLiteConstraintException")
    fun insertVehicleThrowsLocalDbErrorWhenVehicleDaoThrowsSQLiteConstraintException() = runTest {
        // Given
        val vehicle = TestVehicleFactory.generateVehicle()
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.insertVehicle(vehicleEntity) } throws SQLiteConstraintException()
        // When
        val result = runCatching { vehiclesRepository.insertVehicle(vehicle) }
        // Then
        assert(result.exceptionOrNull() is VehicleError.LocalDbError)
    }

    @Tag("insertVehicle")
    @Test
    @DisplayName("insertVehicle throws NAME_EXISTS when vehicleDao throws SQLiteConstraintException")
    fun insertVehicleThrowsNAME_EXISTSWhenVehicleDaoThrowsSQLiteConstraintException() = runTest {
        // Given
        val vehicle = TestVehicleFactory.generateVehicle()
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.insertVehicle(vehicleEntity) } throws SQLiteConstraintException()
        // When
        val result = runCatching { vehiclesRepository.insertVehicle(vehicle) }
        // Then
        val expectation = result.exceptionOrNull()
        assert(
            expectation is VehicleError.LocalDbError
            && expectation.key == LocalDbErrorKey.NAME_EXISTS
        )
    }

    @Tag("insertVehicle")
    @Test
    @DisplayName("insertVehicle throws Exception when vehicleDao throws Exception")
    fun insertVehicleThrowsExceptionWhenVehicleDaoThrowsException() = runTest {
        // Given
        val vehicle = TestVehicleFactory.generateVehicle()
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.insertVehicle(vehicleEntity) } throws IllegalArgumentException("Message")
        // When
        val result = runCatching { vehiclesRepository.insertVehicle(vehicle) }
        // Then
        assert(result.exceptionOrNull() is VehicleError.UnknownVehicleError)
    }

    @Tag("insertVehicle")
    @Test
    @DisplayName("insertVehicle throws Unknown when vehicleDao throws Exception")
    fun insertVehicleThrowsUnknownWhenVehicleDaoThrowsException() = runTest {
        // Given
        val vehicle = TestVehicleFactory.generateVehicle()
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.insertVehicle(vehicleEntity) } throws IllegalArgumentException("Message")
        // When
        val result = runCatching { vehiclesRepository.insertVehicle(vehicle) }
        // Then
        assert(result.exceptionOrNull() is VehicleError.UnknownVehicleError)
    }

    @Tag("loadVehicle")
    @Test
    @DisplayName("loadVehicle should call vehicleDao loadVehicleById")
    fun loadVehicleShouldCallVehicleDaoLoadVehicleById() = runTest {
        // Given
        val vehicleId = 23L
        val vehicle = TestVehicleFactory.generateVehicle(id = vehicleId)
        val vehicleEntity = vehicle.toEntity()
        coEvery { vehicleDao.loadVehicleById(vehicleId) } returns vehicleEntity
        // When
        vehiclesRepository.loadVehicle(vehicleId)
        // Then
        coVerify { vehicleDao.loadVehicleById(vehicleId) }
    }

    @Tag("loadVehicle")
    @Test
    @DisplayName("loadVehicle throws NOT_FOUND when vehicleDao returns null")
    fun loadVehicleThrowsNOT_FOUNDWhenVehicleDaoReturnsNull() = runTest {
        // Given
        val vehicleId = 23L
        coEvery { vehicleDao.loadVehicleById(vehicleId) } returns null
        // When
        val result = runCatching { vehiclesRepository.loadVehicle(vehicleId) }
        // Then
        val expectation = result.exceptionOrNull()
        assert(
            expectation is VehicleError.LocalDbError
            && expectation.key == LocalDbErrorKey.NOT_FOUND
        )
    }

    @Tag("loadVehicle")
    @Test
    @DisplayName("loadVehicle throws Exception when vehicleDao throws Exception")
    fun loadVehicleThrowsUnknownWhenVehicleDaoThrowsException() = runTest {
        // Given
        val vehicleId = 23L
        coEvery { vehicleDao.loadVehicleById(vehicleId) } throws IllegalArgumentException("Message")
        // When
        val result = runCatching { vehiclesRepository.loadVehicle(vehicleId) }
        // Then
        assert(result.exceptionOrNull() is VehicleError.UnknownVehicleError)
    }
}
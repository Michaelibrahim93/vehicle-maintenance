package com.mike.maintenancealarm.data.repo

import com.mike.maintenancealarm.data.storage.db.dao.VehicleDao
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VehiclesRepositoryImplTest {
    private lateinit var vehicleDao: VehicleDao
    private lateinit var vehiclesRepository: VehiclesRepositoryImpl

    @BeforeEach
    fun setUp() {
        vehicleDao = mockk()
        vehiclesRepository = VehiclesRepositoryImpl(vehicleDao)
    }

    @Test
    fun insertVehicle() {
    }

    @Test
    fun loadVehicle() {
    }
}
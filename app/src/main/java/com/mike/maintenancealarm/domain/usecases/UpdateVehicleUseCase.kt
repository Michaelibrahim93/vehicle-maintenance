package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.errors.VehicleError
import com.mike.maintenancealarm.data.vo.errors.VehicleErrorFactory
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

interface UpdateVehicleUseCase {
    suspend fun executeUpdateKm(vehicleId: Long, newKMs: Double)
    suspend fun executeUpdateStatus(vehicleId: Long)
}

class UpdateVehicleUseCaseImpl @Inject constructor(
    private val vehiclesRepository: VehiclesRepository,
    private val vehiclePartsRepository: VehiclePartsRepository
) : UpdateVehicleUseCase {
    override suspend fun executeUpdateKm(vehicleId: Long, newKMs: Double) {
        updateVehicleStatus(
            vehicleId = vehicleId,
            loadVehicle = {
                val vehicle = vehiclesRepository.loadVehicle(vehicleId)

                val updatedVehicle = vehicle.copy(
                    currentKM = newKMs,
                    lastKmUpdate = Date()
                )
                updatedVehicle
            }
        )
    }

    override suspend fun executeUpdateStatus(vehicleId: Long) {
        updateVehicleStatus(
            vehicleId = vehicleId,
            loadVehicle = { vehiclesRepository.loadVehicle(vehicleId) }
        )
    }

    private suspend fun updateVehicleStatus(
        vehicleId: Long,
        loadVehicle: suspend () -> Vehicle
    ) {
        try {
            val vehicle = loadVehicle()

            val vehicleParts = vehiclePartsRepository.loadVehicleParts(vehicleId)
            val vehicleStatus = vehicle.updateStatus(vehicleParts)

            // Update the vehicle's KMs
            vehiclesRepository.updateVehicle(
                vehicle = vehicle.copy(vehicleStatus = vehicleStatus.vehicleStatus)
            )
        } catch (t: Throwable) {
            Timber.d(t)
            throw t
        }
    }
}


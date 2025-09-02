package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.domain.repos.VehiclePartsRepository
import com.mike.maintenancealarm.domain.repos.VehiclesRepository
import com.mike.maintenancealarm.domain.vo.VehiclePart
import timber.log.Timber
import javax.inject.Inject

interface AddVehiclePartUseCase {
    @Throws
    suspend fun execute(
        vehiclePart: VehiclePart
    )
}

class AddVehiclePartUseCaseImpl @Inject constructor(
    private val vehiclesRepository: VehiclesRepository,
    private val vehiclePartsRepository: VehiclePartsRepository
) : AddVehiclePartUseCase {

    override suspend fun execute(
        vehiclePart: VehiclePart
    ) {
        try {
            val vehicle = vehiclesRepository.loadVehicle(vehiclePart.vehicleId)
            if (vehiclePart.id > 0)
                vehiclePartsRepository.updateVehiclePart(vehiclePart)
            else
                vehiclePartsRepository.insertVehiclePart(vehiclePart)
            // Update the vehicle with the new part status
            vehiclesRepository.updateVehicle(
                vehicle = vehicle.updateStatus(listOf(vehiclePart))
            )
        } catch (t: Throwable) {
            Timber.d(t)
            throw t
        }
    }
}
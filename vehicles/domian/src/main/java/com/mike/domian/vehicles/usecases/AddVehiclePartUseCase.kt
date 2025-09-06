package com.mike.domian.vehicles.usecases

import com.mike.domian.vehicles.models.VehiclePart
import com.mike.domian.vehicles.repos.VehiclePartsRepository
import com.mike.domian.vehicles.repos.VehiclesRepository
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
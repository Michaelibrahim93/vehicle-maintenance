package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.domain.repos.VehiclesRepository
import com.mike.maintenancealarm.domain.models.Vehicle
import com.mike.domain.VehicleError
import timber.log.Timber
import javax.inject.Inject
import kotlin.jvm.Throws

interface AddVehicleUseCase {
    @Throws
    suspend fun execute(vehicle: Vehicle)
}

class AddVehicleUseCaseImpl @Inject constructor(
    private val vehicleRepository: VehiclesRepository
) : AddVehicleUseCase {

    @Throws(VehicleError::class)
    override suspend fun execute(
        vehicle: Vehicle
    ) {
        try {
            vehicleRepository.insertVehicle(vehicle)
        } catch (t: Throwable) {
            Timber.e(t)
            throw t
        }
    }
}
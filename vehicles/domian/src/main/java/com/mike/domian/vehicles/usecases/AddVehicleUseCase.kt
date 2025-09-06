package com.mike.domian.vehicles.usecases

import com.mike.core.domain.models.VehicleError
import com.mike.domian.vehicles.models.Vehicle
import com.mike.domian.vehicles.repos.VehiclesRepository
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
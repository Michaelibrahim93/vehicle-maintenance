package com.mike.maintenancealarm.domain.usecases

import com.mike.maintenancealarm.data.repo.VehiclesRepository
import timber.log.Timber
import javax.inject.Inject

interface UpdateAllVehiclesStatusUseCase {
    suspend fun execute()
}

class UpdateAllVehiclesStatusUseCaseImpl @Inject constructor(
    private val vehiclesRepository: VehiclesRepository,
    private val updateVehicleUseCase: UpdateVehicleUseCase
): UpdateAllVehiclesStatusUseCase {
    override suspend fun execute() {
        try {
            val allVehiclesIds = vehiclesRepository.loadAllVehiclesIds()
            allVehiclesIds.forEach { vehicleId ->
                updateVehicleUseCase.executeUpdateStatus(vehicleId)
            }
            Timber.d("UpdateAllVehiclesStatusUseCaseImpl: executed")
        } catch (t: Throwable) {
            Timber.w(t)
            throw t
        }
    }
}
package com.mike.maintenancealarm.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.data.vo.errors.VehicleErrorFactory
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
            throw IllegalArgumentException("test")
            val vehicle = vehiclesRepository.loadVehicle(vehiclePart.vehicleId)
            // Check if the vehicle exists
            vehiclePartsRepository.insertVehiclePart(vehiclePart)
            // Update the vehicle with the new part status
            vehiclesRepository.insertVehicle(
                vehicle = vehicle.updateStatus(listOf(vehiclePart))
            )
        } catch (e: SQLiteConstraintException) {
            throw VehicleErrorFactory.vehicleNameExistsError(e)
        } catch (e: IllegalArgumentException) {
            throw VehicleErrorFactory.vehicleNotFoundError(e)
        } catch (t: Throwable) {
            Timber.d(t)
            throw VehicleErrorFactory.unknownError(t)
        }
    }
}
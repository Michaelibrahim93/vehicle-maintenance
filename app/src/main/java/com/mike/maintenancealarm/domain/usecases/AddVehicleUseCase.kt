package com.mike.maintenancealarm.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
import com.mike.maintenancealarm.data.vo.errors.VehicleError
import com.mike.maintenancealarm.data.vo.errors.VehicleErrorFactory
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
        } catch (e: VehicleError.LocalDbError) {
            throw e
        } catch (t: Throwable) {
            throw VehicleErrorFactory.unknownError(t)
        }
    }
}
package com.mike.maintenancealarm.domain.usecases

import android.database.sqlite.SQLiteConstraintException
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicle
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
    @Throws
    override suspend fun execute(
        vehicle: Vehicle
    ) {
        try {
            vehicleRepository.insertVehicle(vehicle)
        } catch (e: SQLiteConstraintException) {
            throw VehicleErrorFactory.vehicleNameExistsError(e)
        } catch (t: Throwable) {
            throw VehicleErrorFactory.unknownError(t)
        }
    }
}
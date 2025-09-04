package com.mike.domain

import com.mike.resources.R

object VehicleErrorFactory {
    fun vehicleNameExistsError(throwable: Throwable): VehicleError.LocalDbError {
        return VehicleError.LocalDbError(
            throwable = throwable,
            key = LocalDbErrorKey.NAME_EXISTS,
            errorName = R.string.error,
            messageRes = R.string.vehicle_name_exists_error_message
        )
    }

    fun vehicleNotFoundError(throwable: Throwable): VehicleError.LocalDbError {
        return VehicleError.LocalDbError(
            throwable = throwable,
            key = LocalDbErrorKey.NOT_FOUND,
            errorName = R.string.error,
            messageRes = R.string.vehicle_not_found_error_message
        )
    }

    fun unknownError(throwable: Throwable): VehicleError.UnknownVehicleError {
        return VehicleError.UnknownVehicleError(
            throwable = throwable,
            errorName = R.string.error,
            messageRes = R.string.generic_error_message
        )
    }
}
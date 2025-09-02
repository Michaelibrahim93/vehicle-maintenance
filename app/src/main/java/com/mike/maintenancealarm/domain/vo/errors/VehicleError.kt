package com.mike.maintenancealarm.domain.vo.errors

import androidx.annotation.StringRes

sealed class VehicleError : Throwable() {
    data class LocalDbError(
        val throwable: Throwable,
        val key: LocalDbErrorKey,
        @StringRes
        val errorName: Int,
        @StringRes
        val messageRes: Int
    ): VehicleError()

    data class UnknownVehicleError(
        val throwable: Throwable,
        @StringRes
        val errorName: Int,
        @StringRes
        val messageRes: Int
    ): VehicleError()
}


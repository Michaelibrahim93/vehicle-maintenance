package com.mike.domain

import androidx.annotation.StringRes

sealed class VehicleError : Throwable() {
    data class LocalDbError(
        val throwable: Throwable,
        val key: LocalDbErrorKey,
        @field:StringRes
        val errorName: Int,
        @field:StringRes
        val messageRes: Int
    ): VehicleError()

    data class UnknownVehicleError(
        val throwable: Throwable,
        @field:StringRes
        val errorName: Int,
        @field:StringRes
        val messageRes: Int
    ): VehicleError()
}


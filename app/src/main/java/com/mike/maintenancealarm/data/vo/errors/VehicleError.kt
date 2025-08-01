package com.mike.maintenancealarm.data.vo.errors

sealed class VehicleError : Throwable() {
    data class LocalDbError(
        val throwable: Throwable,
        val key: LocalDbErrorKey,
        @StringRes
        val errorName: Int? = null,
        @StringRes
        val messageRes: Int? = null
    ) : VehicleError()
}


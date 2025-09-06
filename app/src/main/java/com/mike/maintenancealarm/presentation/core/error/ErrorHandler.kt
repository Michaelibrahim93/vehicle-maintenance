package com.mike.maintenancealarm.presentation.core.error

import android.app.AlertDialog
import android.content.Context
import com.mike.resources.R
import com.mike.core.domain.models.VehicleError

fun handleUiError(
    context: Context,
    throwable: Throwable,
) {
    val errorTitleRes = when (throwable) {
        is VehicleError.LocalDbError -> throwable.errorName
        is VehicleError.UnknownVehicleError -> throwable.errorName
        else -> R.string.error
    }
    val errorMessageRes = when (throwable) {
        is VehicleError.LocalDbError -> throwable.messageRes
        is VehicleError.UnknownVehicleError -> throwable.messageRes
        else -> R.string.generic_error_message
    }
    val errorMessage = context.getString(errorMessageRes)
    val errorTitle = context.getString(errorTitleRes)

    AlertDialog.Builder(context)
        .setTitle(errorTitle)
        .setMessage(errorMessage)
        .setPositiveButton(R.string.ok, null)
        .show()
}
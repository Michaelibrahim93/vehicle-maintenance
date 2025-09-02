package com.mike.maintenancealarm.domain.vo

import android.content.Context
import androidx.annotation.StringRes

data class ValidationInput<T>(
    val input: T,
    val errorMessage: String? = null,
    @StringRes
    val errorStringRes: Int? = null,
) {
    fun isValid(): Boolean {
        return errorMessage == null && errorStringRes == null
    }

    fun errorMessage(context: Context): String? {
        return errorMessage ?: errorStringRes?.let { context.getString(errorStringRes) }
    }
}

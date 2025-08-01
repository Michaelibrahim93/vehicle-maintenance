package com.mike.maintenancealarm.data.vo

import androidx.annotation.StringRes

data class ValidationInput<T>(
    val input: T,
    val errorMessage: String? = null,
    @StringRes
    val errorStringRes: Int? = null,
)

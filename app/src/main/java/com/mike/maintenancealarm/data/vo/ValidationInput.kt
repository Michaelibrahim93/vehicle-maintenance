package com.mike.maintenancealarm.data.vo

data class ValidationInput<T>(
    val input: T,
    val errorMessage: String? = null
)

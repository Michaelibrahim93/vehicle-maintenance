package com.mike.maintenancealarm.utils.validator.rules

interface ValidationRule {
    fun isValid(value: Any?): Boolean
}
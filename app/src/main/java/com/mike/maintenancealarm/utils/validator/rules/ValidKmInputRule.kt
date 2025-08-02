package com.mike.maintenancealarm.utils.validator.rules

class ValidKmInputRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value is String && value.toDoubleOrNull() != null
    }
}
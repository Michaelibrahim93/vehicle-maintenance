package com.mike.core.domain.utils.validator.rules

class ValidKmInputRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value is String && value.toDoubleOrNull() != null && value.toDouble() >= 0.0
    }
}
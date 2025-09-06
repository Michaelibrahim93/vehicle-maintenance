package com.mike.core.domain.utils.validator.rules

class ValidIntRule(
    private val minNumber: Int = 0
) : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value is String && value.toIntOrNull() != null && value.toInt() >= minNumber
    }
}
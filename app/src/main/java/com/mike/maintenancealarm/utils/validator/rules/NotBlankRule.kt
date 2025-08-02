package com.mike.maintenancealarm.utils.validator.rules

class NotBlankRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value is String && value.isNotBlank()
    }
}
package com.mike.maintenancealarm.utils.validator.rules

class NotNullRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value != null
    }
}
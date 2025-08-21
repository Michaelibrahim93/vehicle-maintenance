package com.mike.maintenancealarm.utils.validator.rules

import java.util.Date

class PastDateRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value is Date && value.time <= System.currentTimeMillis()
    }
}
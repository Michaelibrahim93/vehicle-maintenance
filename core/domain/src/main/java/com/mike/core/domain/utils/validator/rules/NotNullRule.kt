package com.mike.core.domain.utils.validator.rules

class NotNullRule : ValidationRule {
    override fun isValid(value: Any?): Boolean {
        return value != null
    }
}
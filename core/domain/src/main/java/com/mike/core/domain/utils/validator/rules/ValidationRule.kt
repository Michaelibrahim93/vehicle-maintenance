package com.mike.core.domain.utils.validator.rules

interface ValidationRule {
    fun isValid(value: Any?): Boolean
}
package com.mike.maintenancealarm.utils.validator

import androidx.annotation.StringRes
import com.mike.maintenancealarm.utils.validator.rules.ValidationRule

data class ValidationRuleError(
    val validationRule: ValidationRule,
    @StringRes
    val errorStringRes: Int
)

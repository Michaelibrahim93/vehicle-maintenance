package com.mike.core.domain.utils.validator

import androidx.annotation.StringRes
import com.mike.core.domain.utils.validator.rules.ValidationRule

data class ValidationRuleError(
    val validationRule: ValidationRule,
    @StringRes
    val errorStringRes: Int
)

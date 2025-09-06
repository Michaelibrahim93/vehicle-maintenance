package com.mike.core.domain.utils.validator

import androidx.annotation.StringRes
import com.mike.core.domain.utils.validator.rules.ValidationRule

class Validator {
    private val rulesMap: Map<String, List<ValidationRuleError>>

    constructor(rules: Map<String, List<ValidationRuleError>>) {
        this.rulesMap = rules
    }

    fun validate(validationMap: Map<String, Any?>): Map<String, Int> {
        val validationResult = mutableMapOf<String, Int>()
        validationMap.forEach { (key, value) ->
            val rules = rulesMap[key] ?: emptyList()
            for (ruleError in rules) {
                if (!ruleError.validationRule.isValid(value)) {
                    validationResult[key] = ruleError.errorStringRes
                    break // Stop at the first invalid rule
                }
            }
        }
        return validationResult
    }

    class Builder {
        private val rulesMap: MutableMap<String, List<ValidationRuleError>> = mutableMapOf()

        fun addRule(
            mapKey: String,
            rule: ValidationRule,
            @StringRes
            errorMessage: Int
        ): Builder {
            rulesMap[mapKey] = rulesMap.getOrDefault(mapKey, emptyList()) +
                    ValidationRuleError(rule, errorMessage)
            return this
        }

        fun build(): Validator {
            return Validator(rulesMap)
        }
    }
}
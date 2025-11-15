package com.mike.profile.presentation.ui.editprofile.models

import com.mike.core.domain.utils.validator.ValidationInput
import com.mike.profile.domain.Profile
import java.util.Date

data class EditProfileUiState(
    val name: ValidationInput<String> = ValidationInput(""),
    val birthDate: ValidationInput<Date?> = ValidationInput(null),
) {
    companion object {
        const val KEY_NAME = "name"
        const val KEY_BIRTH_DATE = "birthDate"
    }
}

fun EditProfileUiState.setName(name: String) =
    copy(name = ValidationInput(name))

fun EditProfileUiState.setBirthDate(date: Date?) =
    copy(birthDate = ValidationInput(date))

fun EditProfileUiState.toProfile(): Profile {
    return Profile(
        name = name.input,
        birthDate = birthDate.input ?: Date()
    )
}
package com.mike.profile.domain

import java.time.LocalDate

data class Profile(
    val name: String,
    val birthDate: LocalDate,
)

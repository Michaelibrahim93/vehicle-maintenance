package com.mike.profile.domain

import java.util.Date
import java.util.UUID

data class Profile(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val birthDate: Date,
)

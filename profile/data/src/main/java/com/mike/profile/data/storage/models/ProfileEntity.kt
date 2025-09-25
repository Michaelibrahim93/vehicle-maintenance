package com.mike.profile.data.storage.models

import com.mike.profile.domain.Profile
import kotlinx.serialization.Serializable
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date

@Serializable
data class ProfileEntity(
    val id: String,
    val name: String,
    val birthDate: String,
) {
    companion object {
        const val ID_NOT_CREATED = "NO_PROFILE"

        val EMPTY = ProfileEntity(
            id = ID_NOT_CREATED,
            name = "",
            birthDate = "",
        )
    }
}

fun Profile.toEntity(formatter: SimpleDateFormat): ProfileEntity {
    return ProfileEntity(
        id = id,
        name = name,
        birthDate = formatter.format(birthDate),
    )
}

fun ProfileEntity.toDomain(formatter: SimpleDateFormat): Profile? {
    return if (id == ProfileEntity.ID_NOT_CREATED)
        null
    else
        Profile(
            id = id,
            name = name,
            birthDate = formatter.parse(birthDate, ParsePosition(0)) ?: Date(),
        )
}



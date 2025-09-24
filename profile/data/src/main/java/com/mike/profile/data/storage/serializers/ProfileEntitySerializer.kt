package com.mike.profile.data.storage.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.mike.profile.data.storage.models.ProfileEntity
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class ProfileEntitySerializer : Serializer<ProfileEntity> {
    override val defaultValue: ProfileEntity
        get() = ProfileEntity.Companion.EMPTY

    override suspend fun readFrom(input: InputStream): ProfileEntity {
        return try {
            Json.Default.decodeFromString(
                ProfileEntity.Companion.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }
    }

    override suspend fun writeTo(
        t: ProfileEntity,
        output: OutputStream
    ) {
        output.write(
            Json.Default.encodeToString(ProfileEntity.Companion.serializer(), t)
                .encodeToByteArray()
        )
    }
}
package com.mike.profile.data.repos

import androidx.datastore.core.DataStore
import com.mike.data.StorageDateFormats
import com.mike.profile.data.storage.models.ProfileEntity
import com.mike.profile.data.storage.models.toDomain
import com.mike.profile.data.storage.models.toEntity
import com.mike.profile.domain.Profile
import com.mike.profile.domain.repos.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale

class ProfileRepositoryImpl(
    private val profileStorage: DataStore<ProfileEntity>
) : ProfileRepository {
    private val dateFormat = SimpleDateFormat(StorageDateFormats.STORAGE_DAY_FORMAT, Locale.ENGLISH)
    
    override fun listenToProfileChanges(): Flow<Profile?> {
        return profileStorage.data.map {
            it.toDomain(dateFormat)
        }
    }

    override suspend fun loadProfile(): Profile? {
        return profileStorage.data.map {
            it.toDomain(dateFormat)
        }.first()
    }

    override suspend fun updateProfile(profile: Profile) {
        profileStorage.updateData {
            profile.toEntity(dateFormat)
        }
    }
}
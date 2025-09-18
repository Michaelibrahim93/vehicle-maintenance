package com.mike.profile.domain.repos

import com.mike.profile.domain.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun listenToProfileChanges(): Flow<Profile?>
    suspend fun loadProfile(): Profile
    suspend fun updateProfile(profile: Profile)
}
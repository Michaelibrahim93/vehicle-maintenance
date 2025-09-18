package com.mike.profile.domain.usecases

import com.mike.profile.domain.Profile
import com.mike.profile.domain.repos.ProfileRepository

interface UpdateUserProfileUseCase {
    suspend fun execute(profile: Profile)
}

class UpdateUserProfileUseCaseImpl(
    private val profileRepository: ProfileRepository
): UpdateUserProfileUseCase {
    override suspend fun execute(profile: Profile) {
        profileRepository.updateProfile(profile)
    }

}
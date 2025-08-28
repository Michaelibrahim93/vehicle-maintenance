package com.mike.maintenancealarm.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Inject

interface UserManager {
    fun setUserId(id: Long)
    fun logUserId()
}

class UserManagerImpl @Inject constructor(

): UserManager {
    var mUserId: Long = -1
    override fun setUserId(id: Long) {
        Timber.tag("Test_ActivityComponent").d("setUserId: $id")
        mUserId = id
    }

    override fun logUserId() {
        Timber.tag("Test_ActivityComponent").d("setUserId: $mUserId")
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagersModule {
    @Binds
    abstract fun bindUserManager(
        userManagerImpl: UserManagerImpl
    ): UserManager
}
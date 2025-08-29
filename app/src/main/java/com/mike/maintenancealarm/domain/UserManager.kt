package com.mike.maintenancealarm.domain

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Inject

interface UserManager {
    fun setUserId(id: Long)
    fun logUserId(callingViewModel: String)
}

class UserManagerImpl @Inject constructor(

): UserManager {
    var mUserId: Long = -1
    override fun setUserId(id: Long) {
        Timber.tag("Test_ActivityComponent").d("setUserId: $id")
        mUserId = id
    }

    override fun logUserId(callingViewModel: String) {
        Timber.tag("Test_ActivityComponent").d(": $callingViewModel logUserId: $mUserId")
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object ManagersModule {
    @Provides
    @ActivityRetainedScoped
    fun bindUserManager(
        userManagerImpl: UserManagerImpl
    ): UserManager {
        return userManagerImpl
    }
}
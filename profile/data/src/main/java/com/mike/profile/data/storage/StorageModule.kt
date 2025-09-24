package com.mike.profile.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.mike.profile.data.storage.models.ProfileEntity
import com.mike.profile.data.storage.serializers.ProfileEntitySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun provideStorage(@ApplicationContext context: Context): DataStore<ProfileEntity> {

        val dataStore: DataStore<ProfileEntity> = DataStoreFactory.create(
            serializer = ProfileEntitySerializer(),
            produceFile = {
                File("${context.cacheDir.path}/maintenance_profile.preferences_pb")
            },
            corruptionHandler = ReplaceFileCorruptionHandler { ProfileEntity.EMPTY }
        )

        return dataStore
    }
}


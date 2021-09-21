package com.vehiclemaintenance.dataaccess.db

import android.app.Application
import androidx.room.Room
import com.vehiclemaintenance.dataaccess.db.dao.VehicleDao
import com.vehiclemaintenance.dataaccess.db.dao.VehiclePartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): VehicleDatabase {
        return Room
            .databaseBuilder(application, VehicleDatabase::class.java, "vehicle_db")
            .build()
    }

    @Provides
    fun provideVehicleDao(database: VehicleDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    fun provideVehiclePartDao(database: VehicleDatabase): VehiclePartDao {
        return database.vehiclePartDao()
    }
}
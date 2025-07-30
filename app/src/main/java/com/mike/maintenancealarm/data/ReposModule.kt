package com.mike.maintenancealarm.data

import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {
    @Binds
    @Singleton
    abstract fun bindVehiclesRepository(
        impl: VehiclesRepositoryImpl
    ): VehiclesRepository
}
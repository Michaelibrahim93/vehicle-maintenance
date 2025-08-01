package com.mike.maintenancealarm.data

import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {
    @Binds
    abstract fun bindVehiclesRepository(
        impl: VehiclesRepositoryImpl
    ): VehiclesRepository
}
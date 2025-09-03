package com.mike.maintenancealarm.data

import com.mike.maintenancealarm.domain.repos.VehiclePartsRepository
import com.mike.maintenancealarm.data.repos.VehiclePartsRepositoryImpl
import com.mike.maintenancealarm.domain.repos.VehiclesRepository
import com.mike.maintenancealarm.data.repos.VehiclesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class ReposModule {
    @Binds
    abstract fun bindVehiclesRepository(
        impl: VehiclesRepositoryImpl
    ): VehiclesRepository

    @Binds
    abstract fun bindVehiclePartsRepository(
        impl: VehiclePartsRepositoryImpl
    ): VehiclePartsRepository
}
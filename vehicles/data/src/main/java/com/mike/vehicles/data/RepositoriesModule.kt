package com.mike.vehicles.data

import com.mike.domian.vehicles.repos.*
import com.mike.vehicles.data.repos.VehiclePartsRepositoryImpl
import com.mike.vehicles.data.repos.VehiclesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {
    @Binds
    abstract fun bindVehiclesRepository(
        impl: VehiclesRepositoryImpl
    ): VehiclesRepository

    @Binds
    abstract fun bindVehiclePartsRepository(
        impl: VehiclePartsRepositoryImpl
    ): VehiclePartsRepository
}
package com.mike.domian.vehicles

import com.mike.domian.vehicles.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindAddVehicleUseCase(
        addVehicleUseCaseImpl: AddVehicleUseCaseImpl
    ): AddVehicleUseCase

    @Binds
    abstract fun bindAddVehiclePartUseCase(
        addVehiclePartUseCaseImpl: AddVehiclePartUseCaseImpl
    ): AddVehiclePartUseCase

    @Binds
    abstract fun bindUpdateVehicleUseCase(
        updateVehicleUseCase: UpdateVehicleUseCaseImpl
    ): UpdateVehicleUseCase

    @Binds
    abstract fun bindUpdateAllVehiclesStatusUseCase(
        updateAllVehiclesStatusUseCase: UpdateAllVehiclesStatusUseCaseImpl
    ): UpdateAllVehiclesStatusUseCase
}
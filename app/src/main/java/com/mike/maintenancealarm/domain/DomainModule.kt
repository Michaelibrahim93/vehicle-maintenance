package com.mike.maintenancealarm.domain

import com.mike.maintenancealarm.domain.usecases.AddVehiclePartUseCase
import com.mike.maintenancealarm.domain.usecases.AddVehiclePartUseCaseImpl
import com.mike.maintenancealarm.domain.usecases.AddVehicleUseCase
import com.mike.maintenancealarm.domain.usecases.AddVehicleUseCaseImpl
import com.mike.maintenancealarm.domain.usecases.UpdateAllVehiclesStatusUseCase
import com.mike.maintenancealarm.domain.usecases.UpdateAllVehiclesStatusUseCaseImpl
import com.mike.maintenancealarm.domain.usecases.UpdateVehicleUseCase
import com.mike.maintenancealarm.domain.usecases.UpdateVehicleUseCaseImpl
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
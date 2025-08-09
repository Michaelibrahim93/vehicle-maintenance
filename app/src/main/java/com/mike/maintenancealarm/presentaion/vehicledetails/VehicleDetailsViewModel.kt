package com.mike.maintenancealarm.presentaion.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.maintenancealarm.data.repo.VehiclePartsRepository
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.LifeSpan
import com.mike.maintenancealarm.data.vo.VehiclePart
import com.mike.maintenancealarm.data.vo.VehicleParts
import com.mike.maintenancealarm.domain.usecases.AddVehiclePartUseCase
import com.mike.maintenancealarm.presentaion.main.Route
import com.mike.maintenancealarm.utils.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    vehicleRepository: VehiclesRepository,
    partsRepository: VehiclePartsRepository,
    private val addVehiclePartUseCase: AddVehiclePartUseCase,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {

    val vehicleId = savedStateHandle.toRoute<Route.VehicleDetails>().vehicleId
    private val vehicleFlow = vehicleRepository.listenToVehicleById(vehicleId)
    private val partsFlow = partsRepository.listenToVehicleParts(vehicleId)
    val fVehicleDetailsState: StateFlow<VehicleDetailsState> = combine(vehicleFlow, partsFlow) { vehicle, parts ->
        VehicleDetailsState(
            vehicle = vehicle,
            vehicleParts = parts
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VehicleDetailsState()
    )

    fun onEvent(event: VehicleDetailsEvents) {
        Timber.d("onEvent: $event")
        when(event) {
            VehicleDetailsEvents.AddNewPart -> addVehiclePart()
            else -> {}
        }
    }

    private fun addVehiclePart(
        vehicleParts: VehicleParts = fVehicleDetailsState.value.vehicleParts ?: emptyList(),
        vehicleKm: Double = fVehicleDetailsState.value.vehicle?.currentKM ?: 0.0,
        vehicleId: Long = this.vehicleId
    ) = viewModelScope.launch {
        try {
            withContext(dispatcher) {
                addVehiclePartUseCase.execute(
                    vehiclePart = VehiclePart(
                        vehicleId = vehicleId,
                        partName = "Part ${vehicleParts.size.plus(1)}",
                        deploymentDate = Date(),
                        deploymentKM = vehicleKm,
                        lifeSpan = LifeSpan(km = 10000.0, months = 12),
                    )
                )
            }
        } catch (t: Throwable) {
            Timber.w(t)
        }
    }
}
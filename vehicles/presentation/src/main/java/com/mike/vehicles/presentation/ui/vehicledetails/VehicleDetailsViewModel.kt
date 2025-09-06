package com.mike.vehicles.presentation.ui.vehicledetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.mike.core.presentation.navigation.Route
import com.mike.domian.vehicles.repos.VehiclePartsRepository
import com.mike.domian.vehicles.repos.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    vehicleRepository: VehiclesRepository,
    partsRepository: VehiclePartsRepository,
    savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
    val vehicleId = savedStateHandle.toRoute<Route.VehicleDetails>().vehicleId
    private val vehicleFlow = vehicleRepository.listenToVehicleById(vehicleId)
    private val partsFlow = partsRepository.listenToVehicleParts(vehicleId)

    private val sfShowUpdateDialog = MutableStateFlow(false)
    val fVehicleDetailsState: StateFlow<VehicleDetailsState> = combine(
        vehicleFlow, partsFlow, sfShowUpdateDialog
    ) { vehicle, parts, showUpdateDialog ->
        VehicleDetailsState(
            vehicleId = vehicleId,
            vehicle = vehicle,
            vehicleParts = parts,
            showUpdateKmDialog = showUpdateDialog && vehicle != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = VehicleDetailsState()
    )

    init {
        savedStateHandle.keys().forEach {
            Timber.d("key: $it to ${savedStateHandle[it] as Any?}")
        }
    }

    fun onEvent(event: VehicleDetailsEvents) {
        Timber.d("event: $event")
        when(event) {
            is VehicleDetailsEvents.UpdateVehicleKm ->
                sfShowUpdateDialog.value = true
            VehicleDetailsEvents.DismissUpdateKmDialog ->
                sfShowUpdateDialog.value = false
            else -> {}
        }
    }
}
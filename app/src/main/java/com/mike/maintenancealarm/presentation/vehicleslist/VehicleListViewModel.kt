package com.mike.maintenancealarm.presentation.vehicleslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.maintenancealarm.data.repo.VehiclesRepository
import com.mike.maintenancealarm.data.vo.Vehicles
import com.mike.maintenancealarm.presentation.newvehicle.NewVehicleUiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    vehiclesRepository: VehiclesRepository,
) : ViewModel() {
    private val sfVehicles: StateFlow<Vehicles> = vehiclesRepository.listenToAllVehicles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val state: Flow<VehicleListState> = sfVehicles.map { vehicles ->
        VehicleListState(vehicles = vehicles)
    }
    val sharedFlow = MutableSharedFlow<Int>(replay = 3)
    private val channel = Channel<Int>()
    val channelFlow: Flow<Int> = channel.receiveAsFlow()

    val normalFlow = flow {
        for (i in 1..60) {
            delay(50)
            emit(i)
        }
    }

    val stateFlow = MutableStateFlow<String?>("")

    init {
        viewModelScope.ensureActive()

        viewModelScope.launch {
            val job = launch {
                for (i in 1..60) {
                    sharedFlow.emit(i)
                    delay(50)
                }
            }

            launch {
                for (i in 1..60) {
                    channel.send(i)
                    delay(50)
                }
            }

            launch {
                for (i in 1..60){
                    delay(50)
                    stateFlow.value = if (i % 5 == 0) null else "Number: $i"
                }
            }
        }
    }
}
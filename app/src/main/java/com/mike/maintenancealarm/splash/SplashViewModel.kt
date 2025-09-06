package com.mike.maintenancealarm.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mike.core.domain.utils.IoDispatcher
import com.mike.domian.vehicles.usecases.UpdateAllVehiclesStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val updateAllVehiclesStatusUseCase: UpdateAllVehiclesStatusUseCase
) : ViewModel() {
    private val _actionChannel = Channel<SplashUiAction>()
    val actionFlow: Flow<SplashUiAction> = _actionChannel.receiveAsFlow()

    private val delayFlow = flow {
        delay(3000)
        emit(true)
    }

    private val updateAllVehiclesStatusFlow = flow {
        try {
            updateAllVehiclesStatusUseCase.execute()
        } catch (t: Throwable) {
            Timber.w(t)
        }
        emit(true)
    }.flowOn(ioDispatcher)

    private val navigationFlow: Flow<Unit> = combine(
        flow = delayFlow,
        flow2 = updateAllVehiclesStatusFlow
    ) { _, _ ->
        _actionChannel.send(SplashUiAction.NavigateToVehiclesList)
    }

    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.onStart {
        navigationFlow.stateIn(viewModelScope)
            .collect {
                Timber.d("navigationFlow: did run")
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashScreenState()
    )
}
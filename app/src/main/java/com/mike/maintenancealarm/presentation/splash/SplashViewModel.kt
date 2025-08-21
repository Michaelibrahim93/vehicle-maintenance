package com.mike.maintenancealarm.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(SplashScreenState())
    val state: StateFlow<SplashScreenState> = _state.onStart {
        waitAndNavigate()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SplashScreenState(isLoading = false)
    )

    private val _actionChannel = Channel<SplashUiAction>()
    val actionFlow: Flow<SplashUiAction> = _actionChannel.receiveAsFlow()

    private fun waitAndNavigate() = viewModelScope.launch {
        delay(3000)
        _actionChannel.send(SplashUiAction.NavigateToVehiclesList)
    }
}
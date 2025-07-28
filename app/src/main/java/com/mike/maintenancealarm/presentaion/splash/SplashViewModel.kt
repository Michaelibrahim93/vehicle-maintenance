package com.mike.maintenancealarm.presentaion.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
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
        SharingStarted.WhileSubscribed(5000),
        initialValue = SplashScreenState(isLoading = false)
    )

    private val _actionFlow = MutableSharedFlow<SplashUiAction>()
    val actionFlow: SharedFlow<SplashUiAction> = _actionFlow

    private fun waitAndNavigate() = viewModelScope.launch {
        delay(3000)
        _actionFlow.emit(SplashUiAction.NavigateToVehiclesList)
    }
}
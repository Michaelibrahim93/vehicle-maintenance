package com.vehiclemaintenance.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

inline fun ViewModel.launchDataLoad(crossinline block: suspend () -> Unit) {
    viewModelScope.launch {
        block()
    }
}
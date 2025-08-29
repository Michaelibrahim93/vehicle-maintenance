package com.mike.maintenancealarm.presentation.main

import androidx.lifecycle.ViewModel
import com.mike.maintenancealarm.domain.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager,
): ViewModel() {

    fun updateUserId() {
        userManager.setUserId(1)
    }

    fun print() {
        userManager.logUserId("MainViewModel")
    }
}
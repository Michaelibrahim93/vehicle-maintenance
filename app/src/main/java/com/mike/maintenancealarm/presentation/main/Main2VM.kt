package com.mike.maintenancealarm.presentation.main

import androidx.lifecycle.ViewModel
import com.mike.maintenancealarm.domain.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class Main2VM @Inject constructor(
    private val userManager: UserManager
): ViewModel(){
    fun updateUserId() {
        userManager.setUserId(2)
    }

    fun print() {
        userManager.logUserId("Main2VM")
    }
}
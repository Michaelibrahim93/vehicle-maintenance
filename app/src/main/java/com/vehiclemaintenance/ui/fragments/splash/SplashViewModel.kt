package com.vehiclemaintenance.ui.fragments.splash

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.basemodule.viewmodel.BaseViewModel
import com.vehiclemaintenance.utils.launchDataLoad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
    init {
        launchDataLoad { startTimer() }
    }

    @VisibleForTesting
    internal suspend fun startTimer() {
        delay(SPLASH_TIME)
        addAction(SplashFragment.ACTION_TO_HOME)
    }

    companion object {
        @VisibleForTesting
        internal const val SPLASH_TIME = 4000L
    }
}
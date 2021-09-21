package com.vehiclemaintenance.ui.fragments.splash

import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SplashViewModelTest {

    private lateinit var sut: SplashViewModel

    @Before
    fun setup() {
        runOnUiThread {
            sut = SplashViewModel(ApplicationProvider.getApplicationContext())
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun startTimer_checkAddActionHasAction()  = runOnUiThread {
        runBlockingTest {
            sut.startTimer()
            advanceTimeBy(SplashViewModel.SPLASH_TIME)
            val list = sut.ldActions.value
            assertEquals(list?.size, 1)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun startTimer_checkAddActionCaptured()  = runOnUiThread {
        runBlockingTest {
            sut.startTimer()
            advanceTimeBy(SplashViewModel.SPLASH_TIME)
            val list = sut.ldActions.value
            assertEquals(list?.first()?.action, SplashFragment.ACTION_TO_HOME)
        }
    }
}
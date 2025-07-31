package com.mike.maintenancealarm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MaintenanceAlarmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here
        Timber.plant(Timber.DebugTree())
    }
}
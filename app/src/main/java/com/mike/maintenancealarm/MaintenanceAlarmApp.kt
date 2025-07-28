package com.mike.maintenancealarm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MaintenanceAlarmApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here
    }
}
package com.mike.maintenancealarm

import android.app.Application
import android.util.Log
import com.mike.data.ProblemSolving
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MaintenanceAlarmApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val r1 = ProblemSolving.solution(listOf(1, 2, 3, 9, 10), 3)
        val r2 = ProblemSolving.solution(listOf(1, 9, 9, 10, 2), 2)
        val r3 = ProblemSolving.solution(listOf(1, 9, 9, 10, 2), 3)

        val r1Rec = ProblemSolving.solutionRec(listOf(1, 2, 3, 9, 10), 3)
        val r2Rec = ProblemSolving.solutionRec(listOf(1, 9, 9, 10, 2), 2)
        val r3Rec = ProblemSolving.solutionRec(listOf(1, 9, 9, 10, 2), 3)



        Timber.tag("ProblemSolving").d("r1: $r1, r2: $r2, r3: $r3")
        Timber.tag("ProblemSolving").d("r1Rec: $r1Rec, r2Rec: $r2Rec, r3Rec: $r3Rec")
//        Timber.tag("ProblemSolving").d("r1Rec: $r1Rec")
    }
}
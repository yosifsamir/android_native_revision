package com.example.myapplication.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class MyJobService : JobService() {
    // by default it is work on "main thread" . OK .
    // Return true from this method if your job needs to continue running.
    // it will be halted when constraint is no longer satisfied.
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("DDDDD" , "onStartJob is called")

        return true
    }

    // will be called when constraint is no longer satisfied . maybe need to to dispose thread or any other thing .
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("DDDDD" , "onStopJob is called")

        return true // true for reschedule the job again .
    }
}
package com.example.myapplication.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("DDDDD" , "onCreate is called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DDDDD" , "onStartCommand is called")
        Thread {
            Thread.sleep(5000)
            // You can send request here .
            for(i in 0..100){
                if(i == 100){
                    Log.d("DDDDD" , "100 is reached")
                }
            }
//            stopSelf() // This will stop the service and make onDestroy is called . OK .
            // to notify UI use LocalBroadcast or any other technique like Observable in RX or Flow in Coroutine ..... .
        }.start()

        return START_STICKY_COMPATIBILITY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DDDDD" , "onDestroy is called")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("DDDDD" , "onUnbind is called")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("DDDDD" , "onRebind is called")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
//        Log.d("DDDDD" , "onTaskRemoved is called")
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
//        Log.d("DDDDD" , "attachBaseContext is called")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("DDDDD" , "onConfigurationChanged is called")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

}
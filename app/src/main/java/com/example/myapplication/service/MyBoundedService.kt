package com.example.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.util.Log

class MyBoundedService : Service() {

    //Do not implement this interface directly, instead extend from Binder.
    override fun onBind(intent: Intent?): IBinder {
        Log.d("DDDDD" , "onBind is called")
        return MyBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DDDDD" , "onStartCommand is called")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DDDDD" , "onDestroy is called")
    }

}
class MyBinder : Binder() {
    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        Log.d("DDDDD" , "onTransact is called")
        Log.d("DDDDD" , "code : $code , data : $data , reply : $reply , flags : $flags")
        return super.onTransact(code, data, reply, flags)
    }
}
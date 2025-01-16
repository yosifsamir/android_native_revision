package com.example.myapplication.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.myapplication.R

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

// Create a notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "1", "General Notifications", NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = "General notification channel"
            notificationManager.createNotificationChannel(notificationChannel)
        }

// Create the notification
        val notification = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Alarm")
            .setContentText("Alarm")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true) // Enable auto-dismiss on tap
            .build()

        // no need to do this here . requestPermissions should be done when you start the application first time .
// Request runtime notification permissions (API 33+)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(
//                context.,
//                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                1
//            )
//        }

// Display the notification
        notificationManager.notify(1, notification)

    }
}
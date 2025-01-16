package com.example.myapplication.alarm

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/*
    We want to make an app that will give appropriate alarm for each Prayer based on your country .
    I think this will be done using "Timestamp" by using it You can add something called "offset" this offset represent country then it will inc or dec based on this .
 */


class AlarmFragment : BaseFragment<FragmentAlarmBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAlarmBinding = FragmentAlarmBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.prayerTxt.text = "Alfajr"
        var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 5)
        binding.prayerDateTxt.text = "Alfajr: ${simpleDateFormat.format(calendar.timeInMillis)} ==> ${simpleDateFormat.timeZone}"

//        val losAnglesCalendar = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"))
//        losAnglesCalendar.timeInMillis = calendar.timeInMillis
//        simpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a" , Locale("en", "US"))
//        binding.prayerDate2Txt.text = "Time in Los_Angeles: ${simpleDateFormat.format(losAnglesCalendar.time)} ==> ${losAnglesCalendar.timeZone}"

        simpleDateFormat.timeZone = TimeZone.getTimeZone("Asia/Qatar")
        binding.prayerDate2Txt.text = "Alfajr: ${simpleDateFormat.format(calendar.timeInMillis)} ==> ${simpleDateFormat.timeZone}"

        binding.setPrayerAlarmBtn.setOnClickListener {
            val alarmManager = requireContext().getSystemService(AlarmManager::class.java)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                //calendar.timeInMillis,
                Calendar.getInstance().timeInMillis,
                PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    Intent(
                        requireContext(),
                        MyReceiver::class.java
                    ),
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )
            )


        }

        binding.showNotificationBtn.setOnClickListener {
            val notificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

// Create a notification channel for API 26+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    "1", "General Notifications", NotificationManager.IMPORTANCE_HIGH
                )
                notificationChannel.description = "General notification channel"
                notificationManager.createNotificationChannel(notificationChannel)
            }

// Create the notification
            val notification = NotificationCompat.Builder(requireContext(), "1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm")
                .setContentText("Alarm")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true) // Enable auto-dismiss on tap
                .build()

// Request runtime notification permissions (API 33+)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }

// Display the notification
            notificationManager.notify(1, notification)

        }

    }



}
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
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAlarmBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/*
    We want to make an app that will give appropriate alarm for each Prayer based on your country .
    I think this will be done using "Timestamp" by using it You can add something called "offset" this offset represent country then it will inc or dec based on this .
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AlarmFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlarmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlarmFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
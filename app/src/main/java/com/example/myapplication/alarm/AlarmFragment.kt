package com.example.myapplication.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.MainActivity
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
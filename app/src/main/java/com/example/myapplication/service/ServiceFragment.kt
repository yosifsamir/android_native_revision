package com.example.myapplication.service

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentServiceBinding

class ServiceFragment : BaseFragment<FragmentServiceBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentServiceBinding = FragmentServiceBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startServiceBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyService::class.java)
            requireContext().startService(intent)
        }

        binding.stopServiceBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyService::class.java)
            requireContext().stopService(intent)
        }
        binding.startJobSchedulerBtn.setOnClickListener {
            val jobScheduler =
                requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            val jobInfo =
                JobInfo.Builder(1, ComponentName(requireContext(), MyJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setMinimumLatency(1)
                    .build()

            jobScheduler.schedule(jobInfo) // by testing if this happen once . It will never start again . OK .
        }
        binding.startBoundedServiceBtn.setOnClickListener {
            val intent = Intent(requireContext(), MyBoundedService::class.java)
            requireContext().bindService(intent, object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    Log.d("DDDDD" , "onServiceConnected is called")

                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.d("DDDDD" , "onServiceDisconnected is called")

                }

            }, Context.BIND_AUTO_CREATE)
        }


    }


}
package com.example.myapplication.service

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

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
            val jobScheduler = requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

            val jobInfo = JobInfo.Builder(1, ComponentName(requireContext(),MyJobService::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setMinimumLatency(1)
                .build()

            jobScheduler.schedule(jobInfo)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
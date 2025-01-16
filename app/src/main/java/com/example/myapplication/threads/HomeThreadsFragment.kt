package com.example.myapplication.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentHomeThreadsBinding

class HomeThreadsFragment : BaseFragment<FragmentHomeThreadsBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeThreadsBinding = FragmentHomeThreadsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.threadsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_HomeThreadsFragment_to_ThreadFragment)
        }
        binding.rxBtn.setOnClickListener {
            findNavController().navigate(R.id.action_HomeThreadsFragment_to_RxFragment)
        }
        binding.coroutineLifeCycleBtn.setOnClickListener {
            // Todo
        }
    }
}
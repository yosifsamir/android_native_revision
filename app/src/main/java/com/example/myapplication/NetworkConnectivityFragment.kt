package com.example.myapplication

import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentNetworkConnectivityBinding
import com.example.myapplication.databinding.FragmentSecondBinding


class NetworkConnectivityFragment : BaseFragment<FragmentNetworkConnectivityBinding>() {
    private var connectivityManager: ConnectivityManager? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNetworkConnectivityBinding =
        FragmentNetworkConnectivityBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        val activeNetwork =
            connectivityManager?.activeNetwork // It is used to direct traffic to the given Network, either on a Socket basis through a targeted SocketFactory or process-wide via ConnectivityManager.bindProcessToNetwork.
        Log.d("DDDDD", "activeNetwork = ${activeNetwork}")

        Log.d(
            "DDDDD",
            "connectivityManager.isDefaultNetworkActive = ${connectivityManager?.isDefaultNetworkActive}"
        )

//        checkConnectivity(connectivityManager.isDefaultNetworkActive)
//        // You should use Observer
//
//        connectivityManager.addDefaultNetworkActiveListener {
//            checkConnectivity(connectivityManager.isDefaultNetworkActive)
//        }


        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager?.requestNetwork(networkRequest, networkCallback)


    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            checkConnectivity(true)

        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            checkConnectivity(unmetered)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            checkConnectivity(false)
        }
    }

    private fun checkConnectivity(isActive: Boolean) {
        if (isActive) {
            binding.networkStateTxt.text = "Active"
            binding.networkStateTxt.setTextColor(Color.GREEN)
        } else {
            binding.networkStateTxt.text = "InActive"
            binding.networkStateTxt.setTextColor(Color.RED)
        }
    }

}
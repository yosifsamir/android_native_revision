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
import com.example.myapplication.databinding.FragmentNetworkConnectivityBinding
import com.example.myapplication.databinding.FragmentSecondBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NetworkConnectivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NetworkConnectivityFragment : Fragment() {
    private var connectivityManager: ConnectivityManager? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNetworkConnectivityBinding? = null
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
        _binding = FragmentNetworkConnectivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectivityManager = requireContext().getSystemService(ConnectivityManager::class.java)
        val activeNetwork = connectivityManager?.activeNetwork // It is used to direct traffic to the given Network, either on a Socket basis through a targeted SocketFactory or process-wide via ConnectivityManager.bindProcessToNetwork.
        Log.d("DDDDD" , "activeNetwork = ${activeNetwork}")

        Log.d("DDDDD" , "connectivityManager.isDefaultNetworkActive = ${connectivityManager?.isDefaultNetworkActive}")

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
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            checkConnectivity(unmetered)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            checkConnectivity(false)
        }
    }
    private fun checkConnectivity(isActive : Boolean){
        if(isActive) {
            binding.networkStateTxt.text = "Active"
            binding.networkStateTxt.setTextColor(Color.GREEN)
        } else {
            binding.networkStateTxt.text = "InActive"
            binding.networkStateTxt.setTextColor(Color.RED)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NetworkConnectivityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NetworkConnectivityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
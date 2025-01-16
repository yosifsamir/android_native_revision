package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL


class CoroutineFragment : Fragment() {

    private var job1 : Job? = null
    private var job2 : Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coroutine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job1 = CoroutineScope(Dispatchers.Default).launch {
            var increment = 0
//            while (increment < 1000000000){
//                increment++
//                Log.d("DDDDDDDD" , increment.toString())
//            }
        }
        Log.d("DDDDDDDD" , "onViewCreated is called")

        try {
            job2 = CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL("https://www.google.com")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.connect()
                    BufferedInputStream(connection.inputStream).use {
                        val buffer = ByteArray(1024)
                        var count = 0
                        while (count != -1) {
                            count = it.read(buffer)
                            Log.d("DDDDDDDD" , count.toString())
                        }
                    }
                }catch (exception : Exception){
                    Log.d("DDDDD" , "exception: $exception")
                }

            }
        }catch (exception : Exception){
            Log.d("DDDDD" , "exception: $exception")
        }


        val myFlow = flow {
            emit("Hello")
        }

        CoroutineScope(Dispatchers.Main).launch {
            myFlow.flowOn(Dispatchers.IO).collect {
                Log.d("DDDDDDDD" , it)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        job1?.cancel()
        job2?.cancel()
        job1 = null
        job2 = null
    }

}


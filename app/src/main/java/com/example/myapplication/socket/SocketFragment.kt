package com.example.myapplication.socket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSocketBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

class SocketFragment : BaseFragment<FragmentSocketBinding>() {
    private lateinit var socket: MySocket
    private lateinit var myServerSocket: MyServerSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myServerSocket = MyServerSocket {
            binding.serverResultDataTxt.text = it
        }
        myServerSocket.init()

        Handler(Looper.getMainLooper()).postDelayed({
            socket = MySocket()
            socket.init()
        }, 2000)


    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSocketBinding = FragmentSocketBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendMessageImg.setOnClickListener {
            val messageData = binding.messageEdt.text.toString()
            // The Socket need to listen using (port number + Ip address) use loopback address 127.0.0.1

            socket.sendMessage(messageData)

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        socket.close()
        myServerSocket.close()
    }

}

class MySocket {
    private var socket: Socket? =null
    private var outputStream: OutputStream? = null
    private var isSocketReady = false

    private val scope = CoroutineScope(Dispatchers.IO) // Use IO dispatcher for networking

    // Initialize the socket in a separate coroutine
    fun init() {
        scope.launch {
            try {
                socket = Socket("127.0.0.1", 8080)
                outputStream = socket?.getOutputStream()
                isSocketReady = true
            } catch (e: Exception) {
                e.printStackTrace() // Handle connection failure
            }
        }
    }

    // Send a message using coroutine
    fun sendMessage(message: String) {
        scope.launch {
            // Wait for the socket to be ready
            while (!isSocketReady) {
                delay(100) // Delay instead of sleep to be non-blocking
            }

            try {
                // Send the message once the socket is ready
                outputStream?.write(message.toByteArray())
                outputStream?.flush()
            } catch (e: Exception) {
                e.printStackTrace() // Handle sending failure
            }
        }
    }

    // Close the socket and clean up
    fun close() {
        try {
            socket?.close()
            outputStream?.close()
            scope.cancel()
            socket = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

class MyServerSocket(private val messageListener: (String) -> Unit) {
    private var serverSocket: ServerSocket? =null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null
    private var socket: Socket? = null
    private var isSocketReady = false

    private val scope = CoroutineScope(Dispatchers.IO) // Use IO dispatcher for networking

    // Initialize the server socket in a separate coroutine
    fun init() {
        scope.launch {
            try {
                serverSocket = ServerSocket(8080) // Listen on port 8080
                socket = serverSocket?.accept() // Accept client connections
                outputStream = socket?.getOutputStream()
                createInputStream()
                isSocketReady = true

                // Continuously read incoming messages
                while (true) {
                    readMessage() // Continuously read from the input stream
                }
            } catch (e: Exception) {
                e.printStackTrace() // Handle connection failure
            }
        }
    }

    private  fun createInputStream() {
        inputStream = socket?.getInputStream()
    }

    // Read message from input stream
    private suspend fun readMessage() {
        try {
            val buffer = ByteArray(1024)
            withContext(Dispatchers.IO) {
                val bytesRead = inputStream?.read(buffer)

                // If data is read, process it
                if (bytesRead != null && bytesRead > 0) {
                    val message = String(buffer, 0, bytesRead)

                    // Use the listener to pass the message to the UI (Main thread)
                    withContext(Dispatchers.Main) {
                        messageListener(message)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // Handle read failure
        }
    }

    // Send a message using coroutine
    fun sendMessage(message: String) {
        scope.launch {
            // Wait for the socket to be ready
            while (!isSocketReady) {
                delay(100) // Delay instead of sleep to be non-blocking
            }

            try {
                // Send the message once the socket is ready
                outputStream?.write(message.toByteArray())
                outputStream?.flush()
            } catch (e: Exception) {
                e.printStackTrace() // Handle sending failure
            }
        }
    }

    // Close the socket and clean up
    fun close() {
        try {
            serverSocket?.close()
            socket?.close()
            outputStream?.close()
            inputStream?.close()
            scope.cancel() // Cancel any ongoing coroutines
            serverSocket = null
            socket = null
        } catch (e: Exception) {
            e.printStackTrace() // Handle cleanup failure
        }
    }
}
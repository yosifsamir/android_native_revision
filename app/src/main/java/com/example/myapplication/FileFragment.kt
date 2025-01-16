package com.example.myapplication

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentFileBinding
import com.example.myapplication.databinding.FragmentFirstBinding
import java.io.File
import java.io.FileOutputStream

class FileFragment : Fragment() {

    private var _binding: FragmentFileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.writeToFileBtn.setOnClickListener {
            val text = binding.messageInputEdtTxt.text
            text?.let {
                writeToFile(text.toString())
            }
        }
        binding.readFileBtn.setOnClickListener {
            val text = readFromFile()
            binding.messageTxt.text = text
        }

        binding.saveTextToExternalStorageBtn.setOnClickListener {
            val text = binding.messageInputEdtTxt.text
            text?.let {
                writeToExternalStorage(text.toString())
            }
        }

    }

    private fun writeToExternalStorage(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("dddd" , "Environment.isExternalStorageLegacy() =>" + Environment.isExternalStorageLegacy().toString())
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Log.d("dddd" , "Environment.isExternalStorageManager() =>" + Environment.isExternalStorageManager().toString())
        }
        Log.d("dddd" , "Environment.isExternalStorageRemovable() =>" + Environment.isExternalStorageRemovable())

        val dataDirectory = Environment.getDataDirectory()
        Log.d("dddd", "dataDirectory: $dataDirectory")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val storageDirectory = Environment.getStorageDirectory()
            Log.d("dddd", "storageDirectory: $storageDirectory")
        }
        val externalStorageState = Environment.getExternalStorageState()
        Log.d("dddd", "externalStorageState: $externalStorageState")


//
//        val externalCacheDir = requireContext().externalCacheDir
//        Log.d("FileFragment", "externalCacheDir: $externalCacheDir")
//        val file = File(externalCacheDir, "TTTT.txt")
////        file.writeText(text)
//        FileOutputStream(file, true).use {
//            it.write(text.toByteArray())
//        }

    }

    private fun writeToFile(text: String) {
        val fos = requireContext().openFileOutput("text.txt", MODE_PRIVATE)
        fos.write(text.toByteArray())
        fos.close()
    }

    private fun readFromFile(): String {
        val fis = requireContext().openFileInput("text.txt")
        val text = fis.bufferedReader().use { it.readText() }
        fis.close()
        return text
    }

}
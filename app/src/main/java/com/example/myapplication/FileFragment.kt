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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFileBinding? = null
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
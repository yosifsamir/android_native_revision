package com.example.myapplication.intent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentIntentBinding


class IntentFragment : BaseFragment<FragmentIntentBinding>() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIntentBinding = FragmentIntentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data?.getStringExtra("key")
                binding.resultDataTxt.visibility = View.VISIBLE
                binding.resultDataTxt.text = data


                result.data?.data?.let {
                    binding.image.setImageURI(it)
                }
            }
        }


        binding.phone.setOnClickListener {
            // search on  all app's manifest to detect a specific intent's action,data .... .
            val intent = Intent(Intent.ACTION_VIEW) // many things will appear for you . OK .
            intent.data = Uri.parse("tel:0123456789")
            startActivity(intent)
        }
        binding.calender.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW) // many things will appear for you . OK .
            intent.data = Uri.parse("content://calendar")
            val bundle = Bundle()
//            bundle.putString("calendar_id", "12345678")
            intent.putExtras(bundle)
            startActivity(intent)
        }
        binding.intentActivityUsingActionBtn.setOnClickListener {
            val intent = Intent.createChooser(Intent(Intent.ACTION_VIEW), "Choose an app")
            startActivity(intent)
        }
        binding.intentActivityBtn.setOnClickListener {
            val intent = Intent(requireContext(), IntentActivity::class.java)
            intent.putExtra("key", "value")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.intentActivityWithResultBtn.setOnClickListener {
            val intent = Intent(requireContext(), IntentActivity::class.java)
//            startActivityForResult(intent, 1)
            activityResultLauncher.launch(intent)
        }
    }



}
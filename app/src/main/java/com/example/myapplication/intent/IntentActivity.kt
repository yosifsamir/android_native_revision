package com.example.myapplication.intent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.IntentActivityLayoutBinding

class IntentActivity : AppCompatActivity() {
    private lateinit var galleryResultLauncher: ActivityResultLauncher<Intent>
    private var imageData : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DDDDD" , "onCreate is called")
        val binding = IntentActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galleryResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageData = result.data?.data
                binding.selectedImage.setImageURI(imageData)
            }
        }


        if(intent != null){
            binding.receivedDataTxt.text = intent.getStringExtra("key")
        }
        binding.sendBackDataBtn.setOnClickListener {
            intent.putExtra("key", "Youssef")
//            intent.putExtra("image", imageData)
            intent.data = imageData
            setResult(RESULT_OK,intent)
            finish()
        }
        binding.selectImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryResultLauncher.launch(intent)
        }


    }
}
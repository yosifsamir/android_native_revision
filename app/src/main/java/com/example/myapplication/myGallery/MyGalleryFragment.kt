package com.example.myapplication.myGallery

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentMyGalleryBinding
import com.example.myapplication.databinding.GalleryItemLayoutBinding

// Example on Content Resolver
class MyGalleryFragment : BaseFragment<FragmentMyGalleryBinding>(),
    GalleryAdapter.OnDeleteAttachmentCallback {
    private var myGalleryAdapter: GalleryAdapter = GalleryAdapter(this)

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyGalleryBinding = FragmentMyGalleryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.attachmentsRecyclerView.adapter = myGalleryAdapter

        // Todo delete below comment..
        /*
            We need to access content resolver inside that linked to mobile gallery to read gallery.
         */

        // request permission to read gallery

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, do the action
            val imageUris = loadExternalStorageData()
            myGalleryAdapter.addAllImages(imageUris)
        } else {
            // Request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }



    }

    private fun loadExternalStorageData() :MutableList<Uri> {
//        val contentResolver = requireContext().contentResolver
//        contentResolver.openInputStream(
//            Uri.parse("content://media/external/images/media")
//        )?.bufferedReader()?.use {
//            it.readLines()
//                .forEach { Log.d("DDDDD", it) }
//        }
        /////////////

        /*
            Images: MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            Videos: MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            Audio:  MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            Files:  MediaStore.Files.getContentUri("external")
        */
        val contentResolver = requireContext().contentResolver
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val cursor = contentResolver.query(uri, projection, null, null, null)
        val imagesUri: MutableList<Uri> = mutableListOf()

        cursor?.use {
            val nameColumn = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val idColumn = it.getColumnIndex(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val fileName = it.getString(nameColumn)
                val fileId = it.getLong(idColumn)
                val fileUri = ContentUris.withAppendedId(uri, fileId)

                imagesUri.add(fileUri)
                // Example: Reading the file content as an InputStream
//                try {
//                    val inputStream = contentResolver.openInputStream(fileUri)
//                    // Now you can process the InputStream (e.g., load an image, parse data, etc.)
//                    Log.d("ExternalStorage", "Reading file: $fileName")
//
//                    // Don't forget to close the inputStream when done
//                    inputStream?.close()
//                } catch (e: Exception) {
//                    Log.e("ExternalStorage", "Failed to open file: $fileName", e)
//                }
            }
        } ?: run {
            Log.e("ExternalStorage", "Failed to query external storage")
        }

        return imagesUri

    }

    // Handle the permission result
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            Log.d("DDDDD", "1")
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform the action
                loadExternalStorageData()


            } else {
                // Permission denied, show a message to the user
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDeleteAttachment(position: Int) {
        //need to delete using Content Resolver to affect to outside (in Gallery). OK.
        myGalleryAdapter.deleteAttachment(position)
    }


}
class GalleryAdapter(private val onDeleteAttachmentCallback: OnDeleteAttachmentCallback) : RecyclerView.Adapter<GalleryViewHolder>() {
    private val images: MutableList<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = GalleryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.view.apply {
//            val bitmap = BitmapFactory.decodeFile(images[position].absolutePath)
//            attachmentImg.setImageBitmap(bitmap)

            attachmentImg.setImageURI(images[position])
            deleteAttachmentBtn.setOnClickListener {
                onDeleteAttachmentCallback.onDeleteAttachment(position)
            }
            attachmentImg.setOnClickListener {
                val previewImage = ImageView(holder.view.root.context)
                previewImage.layoutParams = ViewGroup.LayoutParams(500, 500)
                previewImage.setImageURI(images[position])

                AlertDialog.Builder(holder.view.root.context, android.R.style.Theme_Dialog,) // theme is very important . to control background color . OK.
                    .setView(previewImage)
                    .setCancelable(true)
                    .create()
                    .show()

            }
        }

    }

    fun addAllImages(images : List<Uri>){
        this.images.clear()
        this.images.addAll(images)
        notifyItemRangeInserted(0,images.size)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun deleteAttachment(position: Int) {
        images.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, images.size)
    }

    interface OnDeleteAttachmentCallback {
        fun onDeleteAttachment(position: Int)
    }
}

class GalleryViewHolder(val view: GalleryItemLayoutBinding) : RecyclerView.ViewHolder(view.root)
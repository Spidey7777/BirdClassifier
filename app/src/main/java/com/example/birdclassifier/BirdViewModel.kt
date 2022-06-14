package com.example.birdclassifier

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BirdViewModel : ViewModel() {

//    fun pickImageGallery() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_REQUEST_CODE)
//    }

//    private val _imageUri = MutableLiveData<Uri>()
//    val imageUri: LiveData<Uri>
//    get() = _imageUri

//    lateinit var imageUri: Uri
}
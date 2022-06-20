package com.example.birdclassifier

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.provider.MediaStore
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.birdclassifier.ml.LiteModelAiyVisionClassifierBirdsV13
import org.tensorflow.lite.support.image.TensorImage
import java.net.URI
import kotlin.coroutines.coroutineContext

class BirdViewModel(val application: Application, val args: Uri): ViewModel() {

    fun getBirdName(image: Bitmap) : String? {
//        val model = context?.let { LiteModelAiyVisionClassifierBirdsV13.newInstance(it) }
        val model = LiteModelAiyVisionClassifierBirdsV13.newInstance(application)
        val tensorImage = TensorImage.fromBitmap(image)
        val outputs = model.process(tensorImage)
        val probability = outputs.probabilityAsCategoryList

        var max = 0.0F
        var index = 0
        for (i in probability) {
            if (max < i.score) {
                max = i.score
                index = probability.indexOf(i)
            }
        }
        model.close()

        val output = probability.get(index)?.label
        return output
    }

    fun uriToBitmap(uri: Uri) : Bitmap {
        val img_btmp = MediaStore.Images.Media.getBitmap(application.contentResolver, args)
        return  img_btmp
    }

    class Factory(val application: Application, val args: Uri) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BirdViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BirdViewModel(application, args) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
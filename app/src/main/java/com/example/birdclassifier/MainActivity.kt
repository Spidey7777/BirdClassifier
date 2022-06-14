package com.example.birdclassifier

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Gallery
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.birdclassifier.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val IMAGE_REQUEST_CODE = 100
    private lateinit var viewModel: BirdViewModel
    private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(BirdViewModel::class.java)
    }



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
//            imageUri = data?.data!!
//            viewModel.imageUri = imageUri
//        }
//    }
}
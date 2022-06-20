package com.example.birdclassifier
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.birdclassifier.databinding.FragmentMainBinding
import com.example.birdclassifier.ml.LiteModelAiyVisionClassifierBirdsV13
import org.tensorflow.lite.support.image.TensorImage

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.mainButton.visibility = View.INVISIBLE

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.birdImage.setImageURI(it)
                binding.mainButton.visibility = View.VISIBLE
                imageUri = it
            }
        )

        binding.birdImage.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.mainButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainFragmentToPredictionFragment2(imageUri.toString()))
        }
        return binding.root
    }
}
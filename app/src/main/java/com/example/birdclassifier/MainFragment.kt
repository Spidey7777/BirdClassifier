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
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.birdclassifier.databinding.FragmentMainBinding
import com.example.birdclassifier.ml.LiteModelAiyVisionClassifierBirdsV13
import org.tensorflow.lite.support.image.TensorImage

class MainFragment : Fragment() {

    companion object {
        val IMAGE_REQUEST_CODE = 100
    }

    private lateinit var binding: FragmentMainBinding
    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.mainButton.visibility = View.INVISIBLE

        binding.birdImage.setOnClickListener {
//            var resultLauncher = registerForActivityResult(
//                ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == Activity.RESULT_OK) {
////                println(result)
//                }
//            }
            ////////////////////////////////////
//            val intent = Intent(context, )

//            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//            startActivityForResult(gallery, IMAGE_REQUEST_CODE)

            pickImageGallery()
        }

        binding.mainButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(MainFragmentDirections.actionMainFragmentToPredictionFragment2(imageUri.toString()))
        }
        return binding.root
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            //buttons
            binding.birdImage.setImageURI(imageUri)
            binding.mainButton.visibility = View.VISIBLE
            val btmp_img = UriToBitmap(imageUri)
            val model = context?.let { LiteModelAiyVisionClassifierBirdsV13.newInstance(it) }
            val tnsr_img = TensorImage.fromBitmap(btmp_img)
            val outputs = model?.process(tnsr_img)
            val probability = outputs?.probabilityAsCategoryList
            if (model != null) {
                model.close()
            }
//            probability?.toTypedArray()?.sort()
//            model?.close()

//            val index: Int = 0
//            val max: Float? = probability?.get(0)?.score
//            binding.birdName.text = max.toString()
        }
    }

    private fun UriToBitmap(uri: Uri) : Bitmap {
        val img_btmp = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
        return  img_btmp
    }
}
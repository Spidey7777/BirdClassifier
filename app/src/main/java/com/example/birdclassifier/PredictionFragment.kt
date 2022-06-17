package com.example.birdclassifier

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.birdclassifier.databinding.FragmentPredictionBinding
import com.example.birdclassifier.ml.LiteModelAiyVisionClassifierBirdsV13
import org.tensorflow.lite.support.image.TensorImage

class PredictionFragment : Fragment() {

    private lateinit var args: PredictionFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPredictionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prediction, container, false)

        args = PredictionFragmentArgs.fromBundle(requireArguments())

        val uriImage: Uri = args.imageUri.toUri()

        binding.birdImage.setImageURI(uriImage)

        val bitmapImage = uriToBitmap(uriImage)

        val birdName = getBirdName(bitmapImage)

        binding.resultText.text = birdName

        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(PredictionFragmentDirections.actionPredictionFragment2ToMainFragment())
        }
        return binding.root
    }

    private fun getBirdName(image: Bitmap) : String? {
        val model = context?.let { LiteModelAiyVisionClassifierBirdsV13.newInstance(it) }
        val tensorImage = TensorImage.fromBitmap(image)
        val outputs = model?.process(tensorImage)
        val probability = outputs?.probabilityAsCategoryList

        var max = 0.0F
        var index = 0
        if (probability != null) {
            for (i in probability) {
                if (max < i.score) {
                    max = i.score
                    index = probability.indexOf(i)
                }
            }
        }
        if (model != null) {
            model.close()
        }

        val output = probability?.get(index)?.label
        return output
    }

    private fun uriToBitmap(uri: Uri) : Bitmap {
        val img_btmp = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, args.imageUri.toUri())
        return  img_btmp
    }
}
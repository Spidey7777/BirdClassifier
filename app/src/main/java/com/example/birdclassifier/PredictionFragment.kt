package com.example.birdclassifier

import android.app.SearchManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
        val binding: FragmentPredictionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prediction, container, false)
        args = PredictionFragmentArgs.fromBundle(requireArguments())

        val viewModel = ViewModelProvider(this, BirdViewModel.Factory(activity?.application!!, args.imageUri.toUri())).get(BirdViewModel::class.java)

        binding.resultText.isClickable = false


        val uriImage: Uri = args.imageUri.toUri()

        binding.birdImage.setImageURI(uriImage)

        val bitmapImage = viewModel.uriToBitmap(uriImage)
        val birdName = viewModel.getBirdName(bitmapImage)

        binding.resultText.text = birdName
        binding.resultText.isClickable = true
        binding.resultText.underline()

        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(PredictionFragmentDirections.actionPredictionFragment2ToMainFragment())
        }

        binding.resultText.setOnClickListener { view: View ->
            val intent = Intent(Intent.ACTION_WEB_SEARCH)
            val term = binding.resultText.text.toString()
            intent.putExtra(SearchManager.QUERY, term)
            startActivity(intent)
        }

        return binding.root
    }

    private fun TextView.underline() {
        paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}
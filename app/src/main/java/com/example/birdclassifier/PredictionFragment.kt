package com.example.birdclassifier

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.birdclassifier.databinding.FragmentPredictionBinding

class PredictionFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPredictionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prediction, container, false)

        val args = PredictionFragmentArgs.fromBundle(requireArguments())
            binding.birdImage.setImageURI(args.imageUri.toUri())

        binding.lifecycleOwner = this

        binding.backButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(PredictionFragmentDirections.actionPredictionFragment2ToMainFragment())
        }
        return binding.root
    }
}
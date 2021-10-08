package com.prashant.naik.ezcart.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prashant.naik.ezcart.MainActivity
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    lateinit var binding : FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.item = args.item

        (activity as MainActivity).supportActionBar?.title = args.item.itemName

        // Inflate the layout for this fragment
        return binding.root
    }
}
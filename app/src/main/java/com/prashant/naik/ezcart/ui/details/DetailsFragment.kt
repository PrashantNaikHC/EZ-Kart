package com.prashant.naik.ezcart.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prashant.naik.ezcart.MainActivity
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()
    lateinit var binding : FragmentDetailsBinding
    @Inject
    lateinit var factory: DetailsViewModelFactory
    lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
        binding.item = args.item

        setTitle(args.item.itemName)

        binding.addToCartButton.setOnClickListener {
            viewModel.addToCart(args.item)
            Toast.makeText(requireContext(), "${args.item.itemName} added to cart", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setTitle(itemName: String) {
        (activity as MainActivity).supportActionBar?.title = itemName
    }
}
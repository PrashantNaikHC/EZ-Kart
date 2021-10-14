package com.prashant.naik.ezcart.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.naik.ezcart.MainActivity
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.ItemsAdapter
import com.prashant.naik.ezcart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var factory: HomeViewModelFactory
    private val adapter by lazy { ItemsAdapter() }
    private val args by navArgs<HomeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupAdapter()

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        (activity as MainActivity).updateUserProfileDetails(args.userProfile)
        (activity as MainActivity).toolbar.background = resources.getDrawable(R.drawable.ic_logo_header)

        viewModel.loadLoginItems().observe(viewLifecycleOwner, { response ->
            response.let {
                if (it != null) {
                    adapter.setData(it)
                }
            }
        })

        return binding.root
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.imageView.visibility = View.GONE
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        adapter.setClickListener { item ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                    item
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).toolbar.background = null
    }
}
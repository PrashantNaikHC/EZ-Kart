package com.prashant.naik.ezcart.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.prashant.naik.ezcart.MainActivity
import com.prashant.naik.ezcart.MainViewModel
import com.prashant.naik.ezcart.MainViewModelFactory
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.BannerAdapter
import com.prashant.naik.ezcart.adapter.ItemsAdapter
import com.prashant.naik.ezcart.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var factory: HomeViewModelFactory
    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
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
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        try {
            mainViewModel.setCurrentUserProfile(args.userProfile)
            Toast.makeText(requireActivity(), "Welcome back ${args.userProfile.firstName}!", Toast.LENGTH_SHORT).show()
        } catch (e:Exception) {
            Log.e("MainActivity", "onCreateView: ${e.message}")
        }
        (activity as? MainActivity)?.updateUserProfileDetails()

        (activity as? MainActivity)?.toolbarLogoLayout?.visibility = View.VISIBLE
        (activity as? MainActivity)?.setCartViewVisibility(true)

        viewModel.loadLoginItems()
        viewModel.loginItems.observe(viewLifecycleOwner, { response ->
            response.let {
                if (it != null) {
                    adapter.setData(it)
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.getItemsOnCart().observe(viewLifecycleOwner, {
            (activity as? MainActivity)?.setNotificationCount(it)
        })

        val adapter: PagerAdapter = BannerAdapter(
            bannerImages = listOf(
                ResourcesCompat.getDrawable(resources, R.drawable.banner_01, null),
                ResourcesCompat.getDrawable(resources, R.drawable.banner_02, null),
                ResourcesCompat.getDrawable(resources, R.drawable.banner_03, null),
                ResourcesCompat.getDrawable(resources, R.drawable.banner_04, null),
                ResourcesCompat.getDrawable(resources, R.drawable.banner_05, null)),
            context = requireActivity())
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager, true)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            viewModel.invalidateAndLoadLoginItems()
        }

        return binding.root
    }

    fun clearUserData(){
        viewModel.clearUserData()
    }

    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.viewPager.visibility = View.GONE
            binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
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
        (activity as? MainActivity)?.toolbarLogoLayout?.visibility = View.GONE
        (activity as? MainActivity)?.setCartViewVisibility(false)
    }
}
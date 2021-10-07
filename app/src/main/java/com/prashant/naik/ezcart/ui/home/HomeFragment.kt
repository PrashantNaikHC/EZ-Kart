package com.prashant.naik.ezcart.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.ItemsAdapter
import com.prashant.naik.ezcart.databinding.FragmentHomeBinding
import com.prashant.naik.ezcart.network.RetrofitClient
import com.prashant.naik.ezcart.network.ShoppingApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val adapter by lazy { ItemsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setupAdapter()

        val client = RetrofitClient.getInstance().create(ShoppingApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = client.getProducts()
            withContext(Dispatchers.Main) {
                result.body().let {
                    if (it != null) {
                        adapter.setData(it)
                    }
                }
            }
        }

        return binding.root
    }
    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
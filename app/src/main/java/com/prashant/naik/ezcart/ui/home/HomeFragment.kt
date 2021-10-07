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
        /*adapter.setData(ItemsResult(listOf(
            Item("dollar","Sweet Bread","01-12-2020","Bread",10,"1pkt"),
            Item("dollar","Sweet Bread","01-12-2020","Bread",10,"1pkt"),
            Item("dollar","Sweet Bread","01-12-2020","Bread",10,"1pkt"),
        )))*/

        return binding.root
    }
    private fun setupAdapter() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
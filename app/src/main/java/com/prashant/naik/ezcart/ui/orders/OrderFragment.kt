package com.prashant.naik.ezcart.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.OrdersAdapter
import com.prashant.naik.ezcart.databinding.FragmentOrderBinding
import com.prashant.naik.ezcart.utils.setOrderHeader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val adapter by lazy { OrdersAdapter() }
    @Inject
    lateinit var factory: OrdersViewModelFactory
    lateinit var viewModel: OrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        viewModel = ViewModelProvider(this, factory).get(OrdersViewModel::class.java)
        setupAdapter()

        viewModel.loadOrders().observe(viewLifecycleOwner, { response ->
            response.body()?.let {
                val latestOrder = OrdersAdapter.getLatestOrder(it.orders)
                binding.ordersHeader.setOrderHeader(latestOrder)
                adapter.setData(latestOrder.data, latestOrder.orderDate)
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupAdapter() {
        binding.ordersRecyclerView.adapter = adapter
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
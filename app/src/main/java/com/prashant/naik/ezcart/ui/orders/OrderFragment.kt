package com.prashant.naik.ezcart.ui.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.OrdersAdapter
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.databinding.FragmentOrderBinding
import com.prashant.naik.ezcart.network.RetrofitClient
import com.prashant.naik.ezcart.network.ShoppingApi
import com.prashant.naik.ezcart.utils.setOrderHeader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val adapter by lazy { OrdersAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        setupAdapter()

        val client = RetrofitClient.getInstance().create(ShoppingApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val result = client.getOrders()
            withContext(Dispatchers.Main) {
                result.body().let {
                    if (it != null) {
                        val latestOrder = OrdersAdapter.getLatestOrder(it.orders)
                        binding.ordersHeader.setOrderHeader(latestOrder)
                        adapter.setData(latestOrder.data, latestOrder.orderDate)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupAdapter() {
        binding.ordersRecyclerView.adapter = adapter
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}
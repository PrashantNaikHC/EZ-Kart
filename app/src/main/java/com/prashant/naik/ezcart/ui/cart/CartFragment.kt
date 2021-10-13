package com.prashant.naik.ezcart.ui.cart

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.adapter.CartItemAdapter
import com.prashant.naik.ezcart.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    private val adapter by lazy { CartItemAdapter() }

    @Inject
    lateinit var factory: CartViewModelFactory
    lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        viewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        setupAdapter()
        viewModel.getCartItems()

        binding.cartHeader.text = getHighlightedText()
        binding.placeOrderButton.setOnClickListener {
            updateTotals()
        }

        viewModel.cartItemsList.observe(viewLifecycleOwner, { items ->
            adapter.setData(items.toMutableList())
            updateTotals()
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getHighlightedText(): SpannableString {
        val spannable = SpannableString(getString(R.string.view_cart_header_text))
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            4, // start
            24, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannable
    }

    private fun setupAdapter() {
        binding.cartRecyclerView.adapter = adapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setClickListener { itemPair ->
            viewModel.removeItemFromCart(itemPair.first.itemName)
            Toast.makeText(
                requireActivity(),
                "${itemPair.first.itemName} has been removed from cart",
                Toast.LENGTH_SHORT
            ).show()
            adapter.notifyItemRemoved(itemPair.second)
        }
    }

    private fun updateTotals() {
        binding.totalCartAmount.text = viewModel.getItemTotalPrice().toString()
    }
}
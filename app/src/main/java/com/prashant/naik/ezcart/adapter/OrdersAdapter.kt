package com.prashant.naik.ezcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.databinding.ListOrderBinding
import com.prashant.naik.ezcart.utils.ItemDiffUtil
import java.text.SimpleDateFormat
import java.util.*

class OrdersAdapter : RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {

    var itemsList = emptyList<Item>()
    lateinit var orderDate: String

    class MyViewHolder(
        private val binding: ListOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            // this is for the onCreateViewHolder to return the viewModel
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingView = ListOrderBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(bindingView)
            }
        }

        fun bind(item: Item, orderDate: String) {
            binding.item = item
            binding.orderDate = orderDate
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return  itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemsList[position]
        holder.bind(currentItem, orderDate)
    }

    fun setData(newData: List<Item>, orderDate: String) {
        val diffUtil = ItemDiffUtil(itemsList, newData)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        itemsList = newData
        this.orderDate = orderDate
    }

    companion object {
        fun getLatestOrder(ordersList: List<Order>): Order {
            return ordersList
                .sortedWith { order1, order2 ->
                    val firstDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(order1.orderDate)
                    val secondDate = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(order2.orderDate)
                    firstDate!!.compareTo(secondDate)
                }
                .reversed()
                .first()
        }
    }

}
package com.prashant.naik.ezcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.databinding.ListCartItemBinding
import com.prashant.naik.ezcart.utils.ItemDiffUtil

class CartItemAdapter : RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {

    var itemsList = emptyList<Item>()
    private var clickListener : (Item) -> Unit = {}

    class MyViewHolder(
        private val binding: ListCartItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            // this is for the onCreateViewHolder to return the viewModel
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingView = ListCartItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(bindingView)
            }
        }

        fun bind(item: Item, clickListener: (Item) -> Unit) {
            binding.item = item
            binding.cartItemRemoveButton.setOnClickListener {
                clickListener.invoke(item)
            }
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
        holder.bind(currentItem, clickListener)
    }

    fun setData(newData: List<Item>) {
        val diffUtil = ItemDiffUtil(itemsList, newData)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        itemsList = newData
    }

    fun setClickListener(clickListener: (Item) -> Unit){
        this.clickListener = clickListener
    }
}
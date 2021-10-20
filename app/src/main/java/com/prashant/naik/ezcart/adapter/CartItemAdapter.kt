package com.prashant.naik.ezcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.databinding.ListCartItemBinding
import com.prashant.naik.ezcart.utils.ItemDiffUtil
import com.prashant.naik.ezcart.utils.getMappedImageResource

class CartItemAdapter : RecyclerView.Adapter<CartItemAdapter.MyViewHolder>() {

    private var itemsList = mutableListOf<Item>()
    private var clickListener : (Pair<Item, Int>) -> Unit = {}

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

        fun bind(
            item: Item,
            clickListener: (Pair<Item, Int>) -> Unit,
            position: Int
        ) {
            binding.item = item
            binding.productImage.setImageResource(item.getMappedImageResource())
            binding.cartItemRemoveButton.setOnClickListener {
                clickListener.invoke(Pair(item, position))
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
        holder.bind(currentItem, clickListener, position)
    }

    fun setData(newData: MutableList<Item>) {
        val diffUtil = ItemDiffUtil(itemsList, newData)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        itemsList = newData
    }

    fun setClickListener(clickListener: (Pair<Item, Int>) -> Unit){
        this.clickListener = clickListener
    }
}
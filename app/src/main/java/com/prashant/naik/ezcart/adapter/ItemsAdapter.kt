package com.prashant.naik.ezcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.databinding.ListItemBinding
import com.prashant.naik.ezcart.utils.ItemDiffUtil
import com.prashant.naik.ezcart.utils.getMappedImageResource

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    private var itemsList = emptyList<Item>()
    private var clickListener : (Item) -> Unit = {}

    class MyViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            // this is for the onCreateViewHolder to return the viewModel
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bindingView = ListItemBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(bindingView)
            }
        }

        fun bind(item: Item, clickListner: (Item) -> Unit) {
            binding.item = item
            binding.productImage.setImageResource(item.getMappedImageResource())
            binding.root.setOnClickListener{
                clickListner.invoke(item)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = itemsList[position]
        holder.bind(currentRecipe, clickListener)
    }

    fun setData(newData: List<Item>) {
        val diffUtil = ItemDiffUtil(itemsList, newData)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        itemsList = newData
    }

    fun setClickListener(clickListner: (Item) -> Unit){
        this.clickListener = clickListner
    }
}
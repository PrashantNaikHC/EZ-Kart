package com.prashant.naik.ezcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prashant.naik.ezcart.databinding.ListItemBinding
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.utils.ItemDiffUtil

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    var recipeList = emptyList<Item>()
    private var clickListner : (Item) -> Unit = {}

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
        return recipeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.bind(currentRecipe, clickListner)
    }

    fun setData(newData: ItemsResult) {
        val diffUtil = ItemDiffUtil(recipeList, newData.items)
        DiffUtil.calculateDiff(diffUtil).dispatchUpdatesTo(this)
        recipeList = newData.items
    }

    fun setClickListener(clickListner: (Item) -> Unit){
        this.clickListner = clickListner
    }
}
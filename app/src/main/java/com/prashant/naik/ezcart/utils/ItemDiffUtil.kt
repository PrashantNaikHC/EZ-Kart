package com.prashant.naik.ezcart.utils

import androidx.recyclerview.widget.DiffUtil

class ItemDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // here we will check the referential equality if it is pointing to the same memory location
        // Java equivalent of ==
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        // here we are checking the structural equality, if both objects are actually same
        // Java equivalent of .equals()
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
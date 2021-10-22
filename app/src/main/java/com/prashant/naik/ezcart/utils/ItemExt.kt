package com.prashant.naik.ezcart.utils

import com.prashant.naik.ezcart.R
import com.prashant.naik.ezcart.data.Item

fun Item.getMappedImageResource(): Int {
    return when {
        this.itemName.contains("Bread") -> {
            R.drawable.product_01
        }
        this.itemName.contains("Salt") -> {
            R.drawable.product_04
        }
        this.itemName.contains("Milk") -> {
            R.drawable.product_02
        }
        this.itemName.contains("Rice") -> {
            R.drawable.product_03
        }
        else -> {
            R.drawable.product_02
        }
    }
}

fun Item.getMappedImageResourceUrl(): String {
    return when {
        this.itemName.contains("Bread") -> "https://images.unsplash.com/photo-1598373182133-52452f7691ef?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGJyZWFkfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
        this.itemName.contains("Salt") -> "https://images.unsplash.com/photo-1612078877705-4fd95d3d82c2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNhbHR8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
        this.itemName.contains("Milk") -> "https://images.unsplash.com/photo-1600788907416-456578634209?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bWlsa3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
        this.itemName.contains("Rice") -> "https://images.unsplash.com/photo-1615485737442-7d6ab9f64db9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cmljZSUyMGJhZ3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"
        else -> "https://images.unsplash.com/photo-1598373182133-52452f7691ef?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGJyZWFkfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"
    }
}
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
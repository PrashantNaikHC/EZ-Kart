package com.prashant.naik.ezcart.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("expiry date")
    val expiryDate: String,
    @SerializedName("item name")
    val itemName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: String
)
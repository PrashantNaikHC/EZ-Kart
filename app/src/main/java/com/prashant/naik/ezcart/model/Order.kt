package com.prashant.naik.ezcart.model


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("data")
    val `data`: List<Item>,
    @SerializedName("order date")
    val orderDate: String,
    @SerializedName("order id")
    val orderId: Int,
    @SerializedName("order total")
    val orderTotal: Int,
    @SerializedName("userr id")
    val userrId: Int
)
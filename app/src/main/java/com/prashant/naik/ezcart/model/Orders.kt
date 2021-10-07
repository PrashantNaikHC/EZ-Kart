package com.prashant.naik.ezcart.model


import com.google.gson.annotations.SerializedName

data class OrdersResult(
    @SerializedName("data")
    val orders: List<Order>
)
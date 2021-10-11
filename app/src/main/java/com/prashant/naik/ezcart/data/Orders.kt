package com.prashant.naik.ezcart.data


import com.google.gson.annotations.SerializedName

data class OrdersResult(
    @SerializedName("data")
    val orders: List<Order>
)
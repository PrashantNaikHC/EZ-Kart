package com.prashant.naik.ezcart.model


import com.google.gson.annotations.SerializedName

data class ItemsResult(
    @SerializedName("data")
    val items: List<Item>
)
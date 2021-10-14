package com.prashant.naik.ezcart.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.prashant.naik.ezcart.utils.Constants

@Entity(tableName = Constants.ORDER_ITEMS_DATABASE)
data class Order(
    @SerializedName("data")
    val `data`: List<Item>,
    @SerializedName("order date")
    val orderDate: String,
    @SerializedName("order id")
    val orderId: Int,
    @SerializedName("order total")
    val orderTotal: Int,
    @PrimaryKey
    @SerializedName("userr id")
    val userrId: Int
)
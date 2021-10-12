package com.prashant.naik.ezcart.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.prashant.naik.ezcart.utils.Constants.Companion.ITEMS_DATABASE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = ITEMS_DATABASE)
data class Item(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("expiry date")
    val expiryDate: String,
    @PrimaryKey
    @SerializedName("item name")
    val itemName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: String
) : Parcelable
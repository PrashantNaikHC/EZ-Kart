package com.prashant.naik.ezcart.data.item

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.prashant.naik.ezcart.utils.Constants
import kotlinx.parcelize.Parcelize

// todo : Avoid code duplication
// As the table name has to be different in the entity, This class has been created with same fields as Item class.

@Parcelize
@Entity(tableName = Constants.LOGIN_ITEMS_DATABASE)
data class LoginItem(
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
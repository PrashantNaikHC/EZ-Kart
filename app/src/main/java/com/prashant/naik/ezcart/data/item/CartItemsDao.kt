package com.prashant.naik.ezcart.data.item

import androidx.room.*
import com.prashant.naik.ezcart.data.Item

@Dao
interface CartItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(itemName: Item)

    @Query("DELETE FROM user_items WHERE itemName == :itemName")
    suspend fun removeItemFromCart(itemName: String)

    @Query("SELECT * FROM user_items")
    suspend fun getCartItems(): List<Item>

    @Query("DELETE FROM user_items")
    suspend fun clearCartItems()
}
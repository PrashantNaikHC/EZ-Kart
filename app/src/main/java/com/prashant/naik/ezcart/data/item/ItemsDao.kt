package com.prashant.naik.ezcart.data.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prashant.naik.ezcart.data.Item

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItemToCart(itemName: Item)

    @Query("DELETE FROM user_items WHERE itemName == :itemName")
    suspend fun removeItemFromCart(itemName: String)

    @Query("SELECT * FROM user_items")
    suspend fun getCartItems(): List<Item>

}
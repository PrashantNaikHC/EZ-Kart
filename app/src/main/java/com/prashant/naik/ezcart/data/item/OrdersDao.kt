package com.prashant.naik.ezcart.data.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prashant.naik.ezcart.data.Order

@Dao
interface OrdersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(orders: List<Order>)

    @Query("SELECT * FROM order_items")
    suspend fun getOrderItems() : List<Order>
}
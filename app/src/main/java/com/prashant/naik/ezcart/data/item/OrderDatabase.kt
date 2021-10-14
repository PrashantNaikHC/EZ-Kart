package com.prashant.naik.ezcart.data.item

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.utils.ItemConverter

@TypeConverters(ItemConverter::class)
@Database(entities = [Order::class, Item::class], version = 1, exportSchema = false)
abstract class OrderDatabase: RoomDatabase() {
    abstract fun orderItemsDao(): OrdersDao
}
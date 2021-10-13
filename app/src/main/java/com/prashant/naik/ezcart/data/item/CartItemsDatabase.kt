package com.prashant.naik.ezcart.data.item

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prashant.naik.ezcart.data.Item

@Database(entities = [Item::class], version = 3, exportSchema = false)
abstract class CartItemsDatabase : RoomDatabase() {
    abstract fun cartItemsDao(): CartItemsDao
}
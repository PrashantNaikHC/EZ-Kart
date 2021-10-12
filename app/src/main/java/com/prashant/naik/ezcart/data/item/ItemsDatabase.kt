package com.prashant.naik.ezcart.data.item

import androidx.room.Database
import androidx.room.RoomDatabase
import com.prashant.naik.ezcart.data.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun itemsDao(): ItemsDao
}
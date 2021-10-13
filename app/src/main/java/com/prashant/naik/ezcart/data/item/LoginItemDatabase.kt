package com.prashant.naik.ezcart.data.item

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LoginItem::class], version = 1, exportSchema = false)
abstract class LoginItemDatabase : RoomDatabase() {
    abstract fun loginItemsDao(): LoginItemsDao
}
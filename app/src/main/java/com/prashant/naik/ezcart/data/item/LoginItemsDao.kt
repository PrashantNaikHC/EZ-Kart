package com.prashant.naik.ezcart.data.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginItems(loginItems: List<LoginItem>)

    @Query("SELECT * FROM login_items")
    suspend fun loadLoginItems(): List<LoginItem>

    @Query("DELETE FROM login_items")
    suspend fun clearLoginItems()
}
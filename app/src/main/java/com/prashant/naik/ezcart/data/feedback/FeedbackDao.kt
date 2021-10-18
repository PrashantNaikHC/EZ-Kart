package com.prashant.naik.ezcart.data.feedback

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: Feedback)


    @Query("SELECT * FROM login_items")
    suspend fun getAllFeedback() : List<Feedback>
}
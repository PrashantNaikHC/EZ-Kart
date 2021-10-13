package com.prashant.naik.ezcart.data.feedback

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Feedback::class], version = 2, exportSchema = false)
abstract class FeedbackDatabase: RoomDatabase() {
    abstract fun feedbackDao(): FeedbackDao
}
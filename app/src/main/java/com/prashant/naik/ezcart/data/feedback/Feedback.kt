package com.prashant.naik.ezcart.data.feedback

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prashant.naik.ezcart.utils.Constants.Companion.FEEDBACK_DATABASE

@Entity(tableName = FEEDBACK_DATABASE)
data class Feedback(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val feedbackText: String,
    val rating: Int
)
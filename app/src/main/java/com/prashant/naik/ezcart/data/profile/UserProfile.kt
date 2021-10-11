package com.prashant.naik.ezcart.data.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile (
    @PrimaryKey
    val userId: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phone: String
)
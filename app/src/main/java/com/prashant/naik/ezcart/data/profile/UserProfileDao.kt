package com.prashant.naik.ezcart.data.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles WHERE userId == :userId")
    suspend fun loginUser(userId: String): UserProfile


    @Query("SELECT * FROM user_profiles")
    suspend fun getAllUserProfiles(): List<UserProfile>
}
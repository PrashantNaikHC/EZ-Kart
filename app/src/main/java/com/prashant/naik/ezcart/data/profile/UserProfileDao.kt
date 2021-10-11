package com.prashant.naik.ezcart.data.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserProfile(userProfile: UserProfile)

}
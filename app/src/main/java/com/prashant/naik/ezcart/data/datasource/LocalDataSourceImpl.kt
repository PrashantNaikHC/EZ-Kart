package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
): LocalDataSource {
    override suspend fun registerProfile(userProfile: UserProfile) {
        userProfileDao.addUserProfile(userProfile)
    }
}
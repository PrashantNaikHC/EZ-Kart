package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.profile.UserProfile

interface LocalDataSource {
    suspend fun registerProfile(userProfile: UserProfile)
}
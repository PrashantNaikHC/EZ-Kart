package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.profile.UserProfile

interface Repository {
    suspend fun registerUser(userProfile: UserProfile)
}
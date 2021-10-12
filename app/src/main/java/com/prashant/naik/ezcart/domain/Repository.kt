package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.profile.UserProfile
import retrofit2.Response

interface Repository {
    suspend fun registerUser(userProfile: UserProfile)
    suspend fun loginUser(userId: String, password: String) : UserProfile

    suspend fun loadLoginItems(): Response<ItemsResult>
}
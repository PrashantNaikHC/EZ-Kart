package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.profile.UserProfile

class RegisterUserUseCase(val repository: Repository) {
    suspend fun registerUser(userProfile: UserProfile) = repository.registerUser(userProfile = userProfile)
}
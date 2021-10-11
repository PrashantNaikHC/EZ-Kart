package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(val repository: Repository) {
    suspend fun registerUser(userProfile: UserProfile) = repository.registerUser(userProfile = userProfile)
}
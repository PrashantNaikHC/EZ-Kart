package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(val repository: Repository) {
    suspend fun loginUser(userId: String, password: String) = repository.loginUser(userId, password)
}
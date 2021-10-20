package com.prashant.naik.ezcart.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {

    fun loginUser(userId: String, password: String) = liveData {
        val userProfile = loginUserUseCase.loginUser(userId)
        when {
            userProfile == null -> emit(Pair(LoginState.USER_NOT_PRESENT, null))
            userProfile.password == password -> emit(Pair(LoginState.SUCCESS, userProfile))
            else -> emit(Pair(LoginState.INCORRECT_PASSWORD, null))
        }
    }

}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory @Inject constructor(private val loginUserUseCase: LoginUserUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(loginUserUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

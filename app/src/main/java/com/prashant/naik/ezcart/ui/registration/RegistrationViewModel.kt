package com.prashant.naik.ezcart.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.RegisterUserUseCase

class RegistrationViewModel(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    fun registerUser(userProfile: UserProfile) = liveData {
        registerUserUseCase.registerUser(userProfile)
        emit(true)
    }

}

class RegistrationViewModelFactory(val registerUserUseCase: RegisterUserUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(RegistrationViewModel::class.java) ->
                RegistrationViewModel(registerUserUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}


package com.prashant.naik.ezcart.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) : ViewModel() {

    fun registerNewUser(userProfile: UserProfile) = viewModelScope.launch {
        registerUserUseCase.registerUser(userProfile)
    }

}

class RegistrationViewModelFactory @Inject constructor(val registerUserUseCase: RegisterUserUseCase) :
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


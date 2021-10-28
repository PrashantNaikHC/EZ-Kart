package com.prashant.naik.ezcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import javax.inject.Inject

class MainViewModel(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {
    lateinit var userProfile: UserProfile

    fun setCurrentUserProfile(userProfile: UserProfile){
        this.userProfile = userProfile
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory @Inject constructor(private val loginUserUseCase: LoginUserUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(loginUserUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
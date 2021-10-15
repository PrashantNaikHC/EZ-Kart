package com.prashant.naik.ezcart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadLoginItemsUseCase
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadItemsUseCase: LoadLoginItemsUseCase,
    private val cartUseCase: CartUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    fun loadLoginItems() = liveData {
        val items = loadItemsUseCase.loadLoginItems()
        emit(items)
    }

    fun getItemsOnCart() = liveData {
        val notificationCount = cartUseCase.getCartItems().count()
        emit(notificationCount)
    }

    fun clearUserData() = viewModelScope.launch {
        loginUserUseCase.logOutUser()
    }

}

class HomeViewModelFactory @Inject constructor(
    val loadItemsUseCase: LoadLoginItemsUseCase,
    private val cartUseCase: CartUseCase,
    private val loginUserUseCase: LoginUserUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(loadItemsUseCase, cartUseCase, loginUserUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

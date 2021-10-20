package com.prashant.naik.ezcart.ui.home

import androidx.lifecycle.*
import com.prashant.naik.ezcart.data.Item
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

    private val _loginItems = MutableLiveData<List<Item>>()
    val loginItems: LiveData<List<Item>> = _loginItems

    fun loadLoginItems() = viewModelScope.launch {
        _loginItems.value = loadItemsUseCase.loadLoginItems()
    }

    fun getItemsOnCart() = liveData {
        val notificationCount = cartUseCase.getCartItems().count()
        emit(notificationCount)
    }

    fun clearUserData() = viewModelScope.launch {
        loginUserUseCase.logOutUser()
    }

    fun invalidateAndLoadLoginItems() = viewModelScope.launch {
        _loginItems.value = loadItemsUseCase.invalidateAndloadLoginItems()
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory @Inject constructor(
    private val loadItemsUseCase: LoadLoginItemsUseCase,
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

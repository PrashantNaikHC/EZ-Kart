package com.prashant.naik.ezcart.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prashant.naik.ezcart.domain.usecases.ShowCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val showCartUseCase: ShowCartUseCase) : ViewModel() {

    fun getCartItems() = liveData {
        val cartItems = showCartUseCase.getCartItems()
        emit(cartItems)
    }

}

class CartViewModelFactory @Inject constructor(val showCartUseCase: ShowCartUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(showCartUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}


package com.prashant.naik.ezcart.ui.cart

import androidx.lifecycle.*
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartUseCase: CartUseCase) : ViewModel() {

    private val _cartItemsList = MutableLiveData<MutableList<Item>>()
    val cartItemsList : LiveData<MutableList<Item>> = _cartItemsList

    fun getCartItems() = viewModelScope.launch {
        _cartItemsList.value = cartUseCase.getCartItems().toMutableList()
    }

    fun removeItemFromCart(itemName: String) = viewModelScope.launch {
        cartUseCase.removeCartItem(itemName)
        getCartItems()
    }

    fun getItemTotalPrice(): Int? {
        return _cartItemsList.value?.map { it.price }?.sum()
    }

}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory @Inject constructor(private val cartUseCase: CartUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(cartUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}


package com.prashant.naik.ezcart.ui.cart

import androidx.lifecycle.*
import com.prashant.naik.ezcart.adapter.OrdersAdapter
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val ordersUseCase: LoadOrdersUseCase
    ) : ViewModel() {

    private val _cartItemsList = MutableLiveData<MutableList<Item>>()
    val cartItemsList : LiveData<MutableList<Item>> = _cartItemsList

    fun getCartItems() = viewModelScope.launch {
        _cartItemsList.value = cartUseCase.getCartItems().toMutableList()
    }

    fun removeItemFromCart(itemName: String) = viewModelScope.launch {
        cartUseCase.removeCartItem(itemName)
        getCartItems()
    }

    fun getItemTotalPrice(): Int {
        return _cartItemsList.value?.map { it.price }?.sum() ?: 0
    }

    fun loadItemsToOrders(cartItems: List<Item>) = viewModelScope.launch {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val currentDate = simpleDateFormat.format(Date())

        val lastOrder = OrdersAdapter.getLatestOrder(ordersUseCase.loadOrders())
        ordersUseCase.addToOrders(Order(
            data = cartItems,
            orderDate = currentDate,
            lastOrder.orderId + 1,
            orderTotal = getItemTotalPrice(),
            userrId = lastOrder.userrId
        ))
    }

}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory @Inject constructor(private val cartUseCase: CartUseCase, private val ordersUseCase: LoadOrdersUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(cartUseCase, ordersUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}


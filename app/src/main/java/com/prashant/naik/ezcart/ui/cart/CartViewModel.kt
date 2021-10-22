package com.prashant.naik.ezcart.ui.cart

import androidx.lifecycle.*
import com.prashant.naik.ezcart.adapter.OrdersAdapter
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.discord.DiscordObject
import com.prashant.naik.ezcart.data.discord.EmbedObject
import com.prashant.naik.ezcart.data.discord.Field
import com.prashant.naik.ezcart.data.discord.Thumbnail
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.DiscordApiUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadOrdersUseCase
import com.prashant.naik.ezcart.utils.Constants.Companion.PLACEHOLDER_IMAGE
import com.prashant.naik.ezcart.utils.getMappedImageResourceUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCase: CartUseCase,
    private val ordersUseCase: LoadOrdersUseCase,
    private val discordApiUseCase: DiscordApiUseCase,
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

    fun loadLastOrder() = liveData {
        val order =  OrdersAdapter.getLatestOrder(ordersUseCase.loadOrders())
        emit(order)
    }

    fun loadItemsToOrders(cartItems: List<Item>, lastOrder: Order, userName: String) = viewModelScope.async {
        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val currentDate = simpleDateFormat.format(Date())

        ordersUseCase.addToOrders(Order(
            data = cartItems,
            orderDate = currentDate,
            lastOrder.orderId + 1,
            orderTotal = getItemTotalPrice(),
            userrId = lastOrder.userrId
        ))
        postToDiscord(cartItems, userName, lastOrder.orderId + 1).await()
    }

    private fun postToDiscord(cartItems: List<Item>, userName: String, orderNumber: Int) = viewModelScope.async {
        val discordObject = DiscordObject(
            avatarUrl = PLACEHOLDER_IMAGE,
            username = userName,
            tts = false,
            content = "Shopping list"
        )
        getItemTotalPrice().toString()
        discordObject.content = "Order #$orderNumber"
        cartItems.forEach {
            discordObject.embeds.add(
                EmbedObject(
                    title = it.itemName,
                    color = 123,
                    thumbnail = Thumbnail(it.getMappedImageResourceUrl()),
                    description = it.desc,
                    fields = mutableListOf(
                        Field("quantity",it.quantity, true),
                        Field("value","${it.price} ${it.currency}", true),
                    )
                )
            )
        }
        discordObject.embeds.add(EmbedObject(title = "Total = ${getItemTotalPrice()} dollars", color = 69966))
        discordApiUseCase.postToDiscord(discordObject)
    }
}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory @Inject constructor(private val cartUseCase: CartUseCase, private val ordersUseCase: LoadOrdersUseCase, private val discordApiUseCase: DiscordApiUseCase,) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(CartViewModel::class.java) ->
                CartViewModel(cartUseCase, ordersUseCase, discordApiUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}


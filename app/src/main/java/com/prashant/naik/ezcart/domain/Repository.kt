package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.data.profile.UserProfile
import retrofit2.Response

interface Repository {
    suspend fun registerUser(userProfile: UserProfile)
    suspend fun loginUser(userId: String, password: String) : UserProfile

    suspend fun loadLoginItems(): List<Item>?
    suspend fun loadOrders(): Response<OrdersResult>

    suspend fun addToCart(item: Item)

    suspend fun getCartItems(): List<Item>
    suspend fun removeCartItem(itemName: String)
}
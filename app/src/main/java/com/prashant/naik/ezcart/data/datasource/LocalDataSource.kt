package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.data.profile.UserProfile

interface LocalDataSource {
    suspend fun registerProfile(userProfile: UserProfile)
    suspend fun loginUserProfile(userId: String): UserProfile

    suspend fun addToCart(item: Item)
    suspend fun getCartItems(): List<Item>
    suspend fun removeCartItem(itemName: String)

    suspend fun loadLoginItems(): List<Item>
    suspend fun saveLoginItemsToDatabase(itemsList: List<Item>)
    suspend fun addFeedback(feedback: Feedback)

    suspend fun loadOrders(): List<Order>
    suspend fun saveOrdersToDatabase(ordersList: List<Order>)

    suspend fun clearUserData()
}
package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.discord.DiscordObject
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.data.profile.UserProfile

interface Repository {
    suspend fun registerUser(userProfile: UserProfile)
    suspend fun loginUser(userId: String) : UserProfile?

    suspend fun loadLoginItems(): List<Item>?
    suspend fun invalidateAndLoadLoginItems(): List<Item>

    suspend fun loadOrders(): List<Order>
    suspend fun addToOrders(order: Order)

    suspend fun addToCart(item: Item)

    suspend fun getCartItems(): List<Item>
    suspend fun removeCartItem(itemName: String)

    suspend fun addFeedback(feedback: Feedback)

    suspend fun clearUserData()

    // Discord API Methods
    suspend fun postToDiscord(discordObject: DiscordObject)
}
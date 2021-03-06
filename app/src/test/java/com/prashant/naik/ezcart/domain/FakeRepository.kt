package com.prashant.naik.ezcart.domain

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.discord.DiscordObject
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.data.profile.UserProfile

class FakeRepository : Repository {
    var loginItems = mutableListOf<Item>()
    var cartItems = mutableListOf<Item>()
    var orders = mutableListOf<Order>()
    var userProfiles = mutableListOf<UserProfile>()
    val feedbacks = mutableListOf<Feedback>()

    override suspend fun registerUser(userProfile: UserProfile) {
        userProfiles.add(userProfile)
    }

    override suspend fun loginUser(userId: String): UserProfile? {
        return userProfiles.find { it.userId == userId }
    }

    override suspend fun loadLoginItems(): List<Item> {
        return loginItems
    }

    override suspend fun invalidateAndLoadLoginItems(): List<Item> {
        loginItems.clear()
        return loginItems
    }

    override suspend fun loadOrders(): List<Order> {
        return orders
    }

    override suspend fun addToOrders(order: Order) {
        cartItems.clear()
        orders = mutableListOf(order)
    }

    override suspend fun addToCart(item: Item) {
        cartItems.add(item)
    }

    override suspend fun getCartItems(): List<Item> {
        return cartItems
    }

    override suspend fun removeCartItem(itemName: String) {
        val foundItem = cartItems.find { it.itemName == itemName }
        cartItems.remove(foundItem)
    }

    override suspend fun addFeedback(feedback: Feedback) {
        feedbacks.add(feedback)
    }

    override suspend fun clearUserData() {
        cartItems.clear()
        loginItems.clear()
    }

    override suspend fun postToDiscord(discordObject: DiscordObject) { }
}
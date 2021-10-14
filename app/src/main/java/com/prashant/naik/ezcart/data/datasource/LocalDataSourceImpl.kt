package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.data.feedback.FeedbackDao
import com.prashant.naik.ezcart.data.item.CartItemsDao
import com.prashant.naik.ezcart.data.item.LoginItemsDao
import com.prashant.naik.ezcart.data.item.OrdersDao
import com.prashant.naik.ezcart.data.profile.UserProfile
import com.prashant.naik.ezcart.data.profile.UserProfileDao
import com.prashant.naik.ezcart.utils.toListOfItems
import com.prashant.naik.ezcart.utils.toListOfLoginItems
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
    private val cartItemsDao: CartItemsDao,
    private val loginItemsDao: LoginItemsDao,
    private val feedbackDao: FeedbackDao,
    private val ordersDao: OrdersDao
): LocalDataSource {
    override suspend fun registerProfile(userProfile: UserProfile) {
        userProfileDao.addUserProfile(userProfile)
    }

    override suspend fun loginUserProfile(userId: String): UserProfile {
        return userProfileDao.loginUser(userId)
    }

    override suspend fun addToCart(item: Item) {
        return cartItemsDao.addItemToCart(item)
    }

    override suspend fun getCartItems(): List<Item> {
        return cartItemsDao.getCartItems()
    }

    override suspend fun removeCartItem(itemName: String) {
        cartItemsDao.removeItemFromCart(itemName)
    }

    override suspend fun loadLoginItems(): List<Item> {
        return loginItemsDao.loadLoginItems().toListOfItems()
    }

    override suspend fun saveLoginItemsToDatabase(itemsList: List<Item>) {
        loginItemsDao.insertLoginItems(itemsList.toListOfLoginItems())
    }

    override suspend fun addFeedback(feedback: Feedback) {
        feedbackDao.insertFeedback(feedback)
    }

    override suspend fun loadOrders(): List<Order> {
        return ordersDao.getOrderItems()
    }

    override suspend fun saveOrdersToDatabase(ordersList: List<Order>) {
        ordersDao.insertOrderItems(ordersList)
    }
}
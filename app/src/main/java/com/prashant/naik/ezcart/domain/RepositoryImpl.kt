package com.prashant.naik.ezcart.domain

import android.util.Log
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.data.datasource.CachedDataSource
import com.prashant.naik.ezcart.data.datasource.LocalDataSource
import com.prashant.naik.ezcart.data.datasource.RemoteDataSource
import com.prashant.naik.ezcart.data.feedback.Feedback
import com.prashant.naik.ezcart.data.profile.UserProfile
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val cachedDataSource: CachedDataSource
) : Repository {
    private val TAG = "RepositoryImpl"

    override suspend fun registerUser(userProfile: UserProfile) {
        localDataSource.registerProfile(userProfile)
    }

    override suspend fun loginUser(userId: String): UserProfile {
        return localDataSource.loginUserProfile(userId)
    }

    override suspend fun loadLoginItems(): List<Item> {
        return loadLoginItemsFromCache()
    }

    override suspend fun loadOrders(): List<Order> {
        return loadOrdersFromCache()
    }

    override suspend fun addToCart(item: Item) {
        localDataSource.addToCart(item)
    }

    override suspend fun getCartItems(): List<Item> {
        return localDataSource.getCartItems()
    }

    override suspend fun removeCartItem(itemName: String) {
        return localDataSource.removeCartItem(itemName)
    }

    override suspend fun addFeedback(feedback: Feedback) {
        localDataSource.addFeedback(feedback)
    }

    override suspend fun clearUserData() {
        localDataSource.clearUserData()
        cachedDataSource.clearUserData()
        Log.d(TAG, "clearUserData: ")
    }

    override suspend fun invalidateAndLoadLoginItems(): List<Item> {
        val newListLoginItems = loadLoginItemsFromNetwork()
        localDataSource.clearUserData()
        localDataSource.saveLoginItemsToDatabase(newListLoginItems)
        cachedDataSource.saveLoginItems(newListLoginItems)
        return newListLoginItems
    }

    private suspend fun loadLoginItemsFromCache(): List<Item> {
        lateinit var itemsList: List<Item>
        try {
            itemsList = cachedDataSource.loadLoginItems()
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
        }
        if (itemsList.isNotEmpty()) {
            Log.d(TAG, "loadLoginItemsFromCache: ")
            return itemsList
        } else {
            itemsList = loadLoginItemsFromDatabase()
            cachedDataSource.saveLoginItems(itemsList)
        }
        return itemsList
    }

    private suspend fun loadOrdersFromCache(): List<Order> {
        lateinit var ordersList: List<Order>
        try {
            ordersList = cachedDataSource.loadOrders()
        } catch (exception: Exception) {
            Log.e(TAG, exception.message.toString())
        }
        if (ordersList.isNotEmpty()) {
            Log.d(TAG, "loadOrdersFromCache: ")
            return ordersList
        } else {
            ordersList = loadOrdersFromDatabase()
            cachedDataSource.saveOrders(ordersList)
        }
        return ordersList
    }

    private suspend fun loadLoginItemsFromDatabase(): List<Item> {
        lateinit var itemsList: List<Item>
        try {
            itemsList = localDataSource.loadLoginItems()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (itemsList.isNotEmpty()) {
            Log.d(TAG, "loadLoginItemsFromDatabase: ")
            return itemsList
        } else {
            itemsList = loadLoginItemsFromNetwork()
            localDataSource.saveLoginItemsToDatabase(itemsList)
        }
        return itemsList
    }

    private suspend fun loadOrdersFromDatabase(): List<Order> {
        lateinit var ordersList: List<Order>
        try {
            ordersList = localDataSource.loadOrders()
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        if (ordersList.isNotEmpty()) {
            Log.d(TAG, "loadLoginItemsFromDatabase: ")
            return ordersList
        } else {
            ordersList = loadOrdersFromNetwork()
            localDataSource.saveOrdersToDatabase(ordersList)
        }
        return ordersList
    }

    private suspend fun loadLoginItemsFromNetwork(): List<Item> {
        lateinit var itemsList: List<Item>
        try {
            val response = remoteDataSource.loadLoginItems()
            Log.d(TAG, "loadLoginItemsFromNetwork: ")
            val body = response.body()
            if (body != null) {
                itemsList = body.items
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return itemsList
    }

    private suspend fun loadOrdersFromNetwork(): List<Order> {
        lateinit var ordersList: List<Order>
        try {
            val response = remoteDataSource.loadOrders()
            Log.d(TAG, "loadOrdersFromNetwork: ")
            val body = response.body()
            if (body != null) {
                ordersList = body.orders
            }
        } catch (exception: Exception) {
            Log.i(TAG, exception.message.toString())
        }
        return ordersList
    }
}
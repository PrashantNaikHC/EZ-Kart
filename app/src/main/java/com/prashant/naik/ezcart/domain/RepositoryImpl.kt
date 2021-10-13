package com.prashant.naik.ezcart.domain

import android.util.Log
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.data.datasource.CachedDataSource
import com.prashant.naik.ezcart.data.datasource.LocalDataSource
import com.prashant.naik.ezcart.data.datasource.RemoteDataSource
import com.prashant.naik.ezcart.data.profile.UserProfile
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val cachedDataSource: CachedDataSource
) : Repository {
    private val TAG = "RepositoryImpl"

    override suspend fun registerUser(userProfile: UserProfile) {
        localDataSource.registerProfile(userProfile)
    }

    override suspend fun loginUser(userId: String, password: String): UserProfile {
        return localDataSource.loginUserProfile(userId, password)
    }

    override suspend fun loadLoginItems(): List<Item> {
        return loadLoginItemsFromCache()
    }

    override suspend fun loadOrders(): Response<OrdersResult> {
        return remoteDataSource.loadOrders()
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

    /*override suspend fun getUpdatedMovieList(): List<Item>? {
        val newListOfMovies = getMoviesFromAPI()
        localDataSource.clearAll()
        localDataSource.saveMoviesToDB(newListOfMovies)
        cacheDataSource.saveMovies(newListOfMovies)
        return newListOfMovies
    }*/
}
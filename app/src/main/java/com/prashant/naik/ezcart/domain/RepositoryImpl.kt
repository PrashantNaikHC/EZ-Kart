package com.prashant.naik.ezcart.domain

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
): Repository {
    override suspend fun registerUser(userProfile: UserProfile) {
        localDataSource.registerProfile(userProfile)
    }

    override suspend fun loginUser(userId: String, password: String): UserProfile {
        return localDataSource.loginUserProfile(userId, password)
    }

    override suspend fun loadLoginItems(): Response<ItemsResult> {
        return remoteDataSource.loadLoginItems();
    }

    override suspend fun loadOrders(): Response<OrdersResult> {
        return remoteDataSource.loadOrders()
    }

    override suspend fun addToCart(item: Item) {
        localDataSource.addToCart(item)
    }
}
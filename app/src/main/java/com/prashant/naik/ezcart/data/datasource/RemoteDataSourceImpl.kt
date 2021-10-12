package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.network.ShoppingApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(val shoppingApi: ShoppingApi) : RemoteDataSource {
    override suspend fun loadLoginItems(): Response<ItemsResult> {
        return shoppingApi.getProducts()
    }

    override suspend fun loadOrders(): Response<OrdersResult> {
        return shoppingApi.getOrders()
    }
}
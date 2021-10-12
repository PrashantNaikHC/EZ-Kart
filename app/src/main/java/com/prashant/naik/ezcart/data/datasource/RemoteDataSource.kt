package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loadLoginItems(): Response<ItemsResult>
    suspend fun loadOrders(): Response<OrdersResult>
}
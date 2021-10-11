package com.prashant.naik.ezcart.network

import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import retrofit2.Response
import retrofit2.http.POST

interface ShoppingApi {

    @POST("/login")
    suspend fun getProducts(): Response<ItemsResult>

    @POST("/getorders")
    suspend fun getOrders(): Response<OrdersResult>
}
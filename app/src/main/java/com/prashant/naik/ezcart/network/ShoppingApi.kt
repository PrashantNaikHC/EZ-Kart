package com.prashant.naik.ezcart.network

import com.prashant.naik.ezcart.model.ItemsResult
import com.prashant.naik.ezcart.model.OrdersResult
import retrofit2.Response
import retrofit2.http.POST

interface ShoppingApi {

    @POST("/login")
    suspend fun getProducts(): Response<ItemsResult>

    @POST("/getorders")
    suspend fun getOrders(): Response<OrdersResult>
}
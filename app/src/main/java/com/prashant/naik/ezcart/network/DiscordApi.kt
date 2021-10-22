package com.prashant.naik.ezcart.network

import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.data.discord.DiscordObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscordApi {
    @POST("/api/webhooks/{webhookId}/{webhookToken}")
    suspend fun getOrders(
        @Path("webhookId") webhookId: String,
        @Path("webhookToken") webhookToken: String,
        @Body body: DiscordObject
    ): Response<OrdersResult>
}
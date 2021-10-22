package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.data.discord.DiscordObject
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loadLoginItems(): Response<ItemsResult>
    suspend fun loadOrders(): Response<OrdersResult>
    suspend fun postToDiscord(discordObject: DiscordObject)
}
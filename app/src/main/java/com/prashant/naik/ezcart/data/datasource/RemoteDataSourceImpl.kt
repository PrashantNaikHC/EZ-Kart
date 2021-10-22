package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.BuildConfig
import com.prashant.naik.ezcart.data.ItemsResult
import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.data.discord.DiscordObject
import com.prashant.naik.ezcart.network.DiscordApi
import com.prashant.naik.ezcart.network.ShoppingApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val shoppingApi: ShoppingApi,
    private val discordApi: DiscordApi
) : RemoteDataSource {
    override suspend fun loadLoginItems(): Response<ItemsResult> {
        return shoppingApi.getProducts()
    }

    override suspend fun loadOrders(): Response<OrdersResult> {
        return shoppingApi.getOrders()
    }

    override suspend fun postToDiscord(discordObject: DiscordObject) {
        discordApi.getOrders(BuildConfig.DISCORD_WEBHOOK_ID,BuildConfig.DISCORD_WEBHOOK_TOKEN, discordObject)
    }
}
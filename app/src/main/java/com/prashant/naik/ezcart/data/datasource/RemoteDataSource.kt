package com.prashant.naik.ezcart.data.datasource

import com.prashant.naik.ezcart.data.ItemsResult
import retrofit2.Response

interface RemoteDataSource {
    suspend fun loadLoginItems(): Response<ItemsResult>
}
package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.OrdersResult
import com.prashant.naik.ezcart.domain.Repository
import retrofit2.Response
import javax.inject.Inject

class LoadOrdersUseCase @Inject constructor(val repository: Repository) {
    suspend fun loadOrders(): Response<OrdersResult> = repository.loadOrders()
}
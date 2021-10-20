package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.Order
import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class LoadOrdersUseCase @Inject constructor(val repository: Repository) {
    suspend fun loadOrders(): List<Order> = repository.loadOrders()
    suspend fun addToOrders(order: Order)  = repository.addToOrders(order)
}
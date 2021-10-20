package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class CartUseCase @Inject constructor(val repository: Repository) {
    suspend fun getCartItems() = repository.getCartItems()
    suspend fun removeCartItem(itemName: String) = repository.removeCartItem(itemName)
}
package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class AddToCartUseCase @Inject constructor(val repository: Repository) {
    suspend fun addToCart(item: Item) = repository.addToCart(item)

}
package com.prashant.naik.ezcart.domain.usecases

import com.prashant.naik.ezcart.domain.Repository
import javax.inject.Inject

class LoadLoginItemsUseCase @Inject constructor(val repository: Repository) {
    suspend fun loadLoginItems() = repository.loadLoginItems()
    suspend fun invalidateAndloadLoginItems() = repository.invalidateAndLoadLoginItems()
}
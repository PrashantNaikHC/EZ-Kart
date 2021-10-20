package com.prashant.naik.ezcart.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prashant.naik.ezcart.domain.usecases.LoadOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val loadOrdersUseCase: LoadOrdersUseCase) : ViewModel() {

    fun loadOrders() = liveData {
        val orders = loadOrdersUseCase.loadOrders()
        emit(orders)
    }

}

@Suppress("UNCHECKED_CAST")
class OrdersViewModelFactory @Inject constructor(private val loadOrdersUseCase: LoadOrdersUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(OrdersViewModel::class.java) ->
                OrdersViewModel(loadOrdersUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
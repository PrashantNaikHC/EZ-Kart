package com.prashant.naik.ezcart.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.usecases.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val addToCartUseCase: AddToCartUseCase) : ViewModel() {

    fun addToCart(item: Item) = viewModelScope.launch {
        addToCartUseCase.addToCart(item)
    }

}

class DetailsViewModelFactory @Inject constructor(val addToCartUseCase: AddToCartUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(DetailsViewModel::class.java) ->
                DetailsViewModel(addToCartUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
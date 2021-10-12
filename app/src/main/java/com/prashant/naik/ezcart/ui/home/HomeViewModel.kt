package com.prashant.naik.ezcart.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.prashant.naik.ezcart.domain.usecases.LoadLoginItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val loadItemsUseCase: LoadLoginItemsUseCase) : ViewModel() {

    fun loadLoginItems() = liveData {
        val items = loadItemsUseCase.loadLoginItems()
        emit(items)
    }

}

class HomeViewModelFactory @Inject constructor(val loadItemsUseCase: LoadLoginItemsUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(loadItemsUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

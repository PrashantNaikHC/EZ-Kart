package com.prashant.naik.ezcart.ui.home

import androidx.lifecycle.*
import com.prashant.naik.ezcart.data.Item
import com.prashant.naik.ezcart.domain.usecases.CartUseCase
import com.prashant.naik.ezcart.domain.usecases.LoadLoginItemsUseCase
import com.prashant.naik.ezcart.domain.usecases.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadItemsUseCase: LoadLoginItemsUseCase,
    private val cartUseCase: CartUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _loginItems = MutableLiveData<List<Item>>()
    val loginItems: LiveData<List<Item>> = _loginItems

    private lateinit var client: HttpClient

    fun loadLoginItems() = viewModelScope.launch {
        _loginItems.value = loadItemsUseCase.loadLoginItems()
    }

    fun getItemsOnCart() = liveData {
        val notificationCount = cartUseCase.getCartItems().count()
        emit(notificationCount)
    }

    fun clearUserData() = viewModelScope.launch {
        loginUserUseCase.logOutUser()
    }

    fun invalidateAndLoadLoginItems() = viewModelScope.launch {
        _loginItems.value = loadItemsUseCase.invalidateAndloadLoginItems()
    }

    fun getUpdatesFromServer() = liveData {
        client = HttpClient {
            install(WebSockets)
        }

        client.webSocket(method = HttpMethod.Get, host = "10.0.2.2", port = 8080, path = "/chat") {
            val messageOutputRoutine = launch {
                try {
                    for (message in incoming) {
                        message as? Frame.Text ?: continue
                        emit(message.readText())
                    }
                } catch (e: Exception) {
                    println("Error while receiving: " + e.localizedMessage)
                }
            }
            messageOutputRoutine.join()
        }
        client.close()
        println("Connection closed. Goodbye!")
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory @Inject constructor(
    private val loadItemsUseCase: LoadLoginItemsUseCase,
    private val cartUseCase: CartUseCase,
    private val loginUserUseCase: LoginUserUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(loadItemsUseCase, cartUseCase, loginUserUseCase)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

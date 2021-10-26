package com.prashant.naik.ezcart.network

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

/**
 * This is the sample usage of sockets using okHttp.
 * Currently we are using ktor.
 */
class ShoppingWebSocketProvider {

    private var _webSocket: WebSocket? = null

    private val socketOkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(39, TimeUnit.SECONDS)
        .hostnameVerifier { _, _ -> true }
        .build()

    @ExperimentalCoroutinesApi
    private var _webSocketListener: ShoppingWebSocketListener? = null

    @ExperimentalCoroutinesApi
    fun startSocket(): Channel<SocketUpdate> =
        with(ShoppingWebSocketListener()) {
            startSocket(this)
            this@with.socketEventChannel
        }

    @ExperimentalCoroutinesApi
    private fun startSocket(webSocketListener: ShoppingWebSocketListener) {
        _webSocketListener = webSocketListener
        _webSocket = socketOkHttpClient.newWebSocket(
            Request.Builder().url("http://www.websocket.org/echo.html").build(),
            webSocketListener
        )
        socketOkHttpClient.dispatcher.executorService.shutdown()
    }

    @ExperimentalCoroutinesApi
    fun stopSocket() {
        try {
            _webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            _webSocket = null
            _webSocketListener?.socketEventChannel?.close()
            _webSocketListener = null
        } catch (ex: Exception) {
        }
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
    }

}
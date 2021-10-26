package com.prashant.naik.ezcart.network

import android.util.Log
import com.prashant.naik.ezcart.network.ShoppingWebSocketProvider.Companion.NORMAL_CLOSURE_STATUS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

/**
 * This is the sample usage of sockets using okHttp.
 * Currently we are using ktor.
 */
@ExperimentalCoroutinesApi
class ShoppingWebSocketListener : WebSocketListener() {
    val TAG = "ShoppingWebSocketListener"
    val socketEventChannel: Channel<SocketUpdate> = Channel(10)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(TAG, "onOpen: ")
        webSocket.send("Hi")
        webSocket.send("Hi again")
        webSocket.send("Hi again again")
        webSocket.send("Hi again again again")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(TAG, "onMessage: ")
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(text))
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(TAG, "onClosing: ")
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(exception = SocketAbortedException()))
        }
        webSocket.close(NORMAL_CLOSURE_STATUS, null)
        socketEventChannel.close()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure: ${t.message}")
        GlobalScope.launch {
            socketEventChannel.send(SocketUpdate(exception = t))
        }
    }

}

class SocketAbortedException : Exception()

data class SocketUpdate(
    val text: String? = null,
    val byteString: ByteString? = null,
    val exception: Throwable? = null
)
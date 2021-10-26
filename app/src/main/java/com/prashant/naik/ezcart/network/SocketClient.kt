package com.prashant.naik.ezcart.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.launch

class SocketClient {
    private lateinit var client: HttpClient

    suspend fun getUpdatesFromServer() {
        client = HttpClient {
            install(WebSockets)
        }

        client.webSocket(method = HttpMethod.Get, host = "10.0.2.2", port = 8080, path = "/chat") {
            val messageOutputRoutine = launch { outputMessages() }
            messageOutputRoutine.join()
        }

        client.close()
        println("Connection closed. Goodbye!")

    }
}

suspend fun DefaultClientWebSocketSession.outputMessages() {
    try {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val LOG_TAG = "SocketClient"
            Log.d(LOG_TAG, "outputMessages: ${message.readText()}")
        }
    } catch (e: Exception) {
        println("Error while receiving: " + e.localizedMessage)
    }
}
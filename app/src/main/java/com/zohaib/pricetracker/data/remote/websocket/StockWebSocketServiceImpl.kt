package com.zohaib.pricetracker.data.remote.websocket

import com.zohaib.pricetracker.common.Constants.SOCKET_URL
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.*

class StockWebSocketServiceImpl : StockWebSocketService {
    //private val client = OkHttpClient()
    private val client = OkHttpClient.Builder()
        .pingInterval(5, java.util.concurrent.TimeUnit.SECONDS) // send ping every 10s
        .build()
    private var webSocket: WebSocket? = null

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean> = _isConnected

    private val _incomingMessages = Channel<String>(Channel.UNLIMITED)
    override fun connect(): Flow<String> {
        val request = Request.Builder()
            .url(SOCKET_URL)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                _isConnected.value = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _incomingMessages.trySend(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                _isConnected.value = false
                webSocket.close(code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _isConnected.value = false
                t.printStackTrace()
            }
        })

        return _incomingMessages.receiveAsFlow()
    }

    override suspend fun send(message: String) {
        webSocket?.send(message)
    }

    override suspend fun disconnect() {
        webSocket?.close(1000, "Client disconnect")
        _isConnected.value = false
        client.dispatcher.executorService.shutdown()
    }
}
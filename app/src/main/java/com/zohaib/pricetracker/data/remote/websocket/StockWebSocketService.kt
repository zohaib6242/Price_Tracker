package com.zohaib.pricetracker.data.remote.websocket

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StockWebSocketService {
    val isConnected: StateFlow<Boolean>
    fun connect(): Flow<String>
    suspend fun send(message: String)
    suspend fun disconnect()
}
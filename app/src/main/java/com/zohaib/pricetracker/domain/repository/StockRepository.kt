package com.zohaib.pricetracker.domain.repository

import com.zohaib.pricetracker.domain.model.Stock
import kotlinx.coroutines.flow.StateFlow

interface StockRepository {
    val isConnected: StateFlow<Boolean>
    val stocks: StateFlow<List<Stock>>
    fun start()
    fun stop()
}
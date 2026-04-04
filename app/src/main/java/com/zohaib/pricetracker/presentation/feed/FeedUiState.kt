package com.zohaib.pricetracker.presentation.feed

import com.zohaib.pricetracker.domain.model.Stock

data class FeedUiState(
    val stocks: List<Stock> = emptyList(),
    val isRunning: Boolean = false,
    val isConnected: Boolean = true
)
package com.zohaib.pricetracker.domain.usecase

import com.zohaib.pricetracker.domain.model.Stock
import com.zohaib.pricetracker.domain.repository.StockRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveStocksUseCase(
    private val repository: StockRepository
) {
    operator fun invoke(): StateFlow<List<Stock>> = repository.stocks
}
package com.zohaib.pricetracker.domain.usecase

import com.zohaib.pricetracker.domain.repository.StockRepository

class StartStockStreamUseCase(
    private val repository: StockRepository
) {
    operator fun invoke() = repository.start()
}
package com.zohaib.pricetracker.domain.usecase

import com.zohaib.pricetracker.domain.repository.StockRepository

class StopStockStreamUseCase(
    private val repository: StockRepository
) {
    operator fun invoke() = repository.stop()
}
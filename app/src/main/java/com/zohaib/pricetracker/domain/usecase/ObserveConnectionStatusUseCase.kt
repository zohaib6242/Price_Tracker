package com.zohaib.pricetracker.domain.usecase

import com.zohaib.pricetracker.domain.repository.StockRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveConnectionStatusUseCase @Inject constructor(
    private val repository: StockRepository
) {
    operator fun invoke(): StateFlow<Boolean> = repository.isConnected
}
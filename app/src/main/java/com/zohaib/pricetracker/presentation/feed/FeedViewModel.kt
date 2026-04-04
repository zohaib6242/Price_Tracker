package com.zohaib.pricetracker.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zohaib.pricetracker.domain.usecase.ObserveConnectionStatusUseCase
import com.zohaib.pricetracker.domain.usecase.ObserveStocksUseCase
import com.zohaib.pricetracker.domain.usecase.StartStockStreamUseCase
import com.zohaib.pricetracker.domain.usecase.StopStockStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FeedViewModel @Inject constructor(
    observeStocksUseCase: ObserveStocksUseCase,
    observeConnectionStatusUseCase: ObserveConnectionStatusUseCase,
    private val startUseCase: StartStockStreamUseCase,
    private val stopUseCase: StopStockStreamUseCase
) : ViewModel() {

    private val _isRunning = MutableStateFlow(false)

    val uiState = combine(
        observeStocksUseCase(),
        _isRunning,
        observeConnectionStatusUseCase()
    ) { stocks, isRunning ,isConnected ->
        FeedUiState(
            stocks = stocks,
            isRunning = isRunning,
            isConnected = isConnected
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FeedUiState()
    )

    fun toggle() {
        if (_isRunning.value) {
            stopUseCase()
        } else {
            startUseCase()
        }
        _isRunning.value = !_isRunning.value
    }
}
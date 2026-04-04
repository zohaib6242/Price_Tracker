package com.zohaib.pricetracker.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zohaib.pricetracker.domain.usecase.ObserveStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    observeStocksUseCase: ObserveStocksUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val symbol: String = checkNotNull(savedStateHandle["symbol"])

    val stock = observeStocksUseCase()
        .map { list -> list.find { it.symbol == symbol } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )
}
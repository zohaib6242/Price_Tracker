package com.zohaib.pricetracker.domain.model

data class Stock(
    val symbol: String,
    val price: Double,
    val change: Double = 0.0
)

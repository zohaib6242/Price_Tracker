package com.zohaib.pricetracker.presentation.details

fun getStockDescription(symbol: String): String {
    return when (symbol) {
        "AAPL" -> "Apple Inc. designs consumer electronics and software."
        "GOOG" -> "Google specializes in internet-related services."
        "TSLA" -> "Tesla focuses on electric vehicles and clean energy."
        "AMZN" -> "Amazon is a global e-commerce and cloud computing giant."
        "MSFT" -> "Microsoft develops software and cloud solutions."
        else -> "This company is a major player in the global market."
    }
}
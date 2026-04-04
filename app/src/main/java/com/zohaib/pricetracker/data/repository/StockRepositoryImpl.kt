package com.zohaib.pricetracker.data.repository

import com.zohaib.pricetracker.data.remote.websocket.StockWebSocketService
import com.zohaib.pricetracker.domain.model.Stock
import com.zohaib.pricetracker.domain.model.StockUpdate
import com.zohaib.pricetracker.domain.repository.StockRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

class StockRepositoryImpl(
    private val webSocketService: StockWebSocketService
) : StockRepository {

    override val isConnected: StateFlow<Boolean> = webSocketService.isConnected

    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    override val stocks: StateFlow<List<Stock>> = _stocks

    // Repository-wide scope
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var job: Job? = null

    private val symbols = listOf(
        "AAPL","GOOG","TSLA","AMZN","MSFT",
        "NVDA","META","NFLX","BABA","INTC",
        "AMD","ORCL","IBM","UBER","LYFT",
        "SHOP","SQ","TWTR","SNAP","ADBE",
        "CRM","PYPL","QCOM","CSCO","SONY"
    )

    override fun start() {
        if (job != null) return  // Already started

        // Initialize stock list with random prices
        _stocks.value = symbols.map { Stock(it, Random.nextDouble(100.0, 500.0)) }

        job = scope.launch {
            // Listen for incoming WebSocket messages
            launch {
                webSocketService.connect().collect { message ->
                    val update = parseMessage(message)
                    updateStock(update)
                }
            }

            // Periodically send updates every 2 seconds
            launch {
                while (isActive) {
                    delay(2000)
                    if (webSocketService.isConnected.value) {
                        symbols.forEach { symbol ->
                            val price = Random.nextDouble(100.0, 500.0)
                            val msg = "$symbol:$price"
                            webSocketService.send(msg)
                        }
                    }
                }
            }
        }
    }

    override fun stop() {
        job?.cancel()  // Cancel all child coroutines
        job = null
    }

    // Parse incoming WebSocket message into StockUpdate
    private fun parseMessage(message: String): StockUpdate {
        val parts = message.split(":")
        return StockUpdate(
            symbol = parts.getOrNull(0) ?: "",
            price = parts.getOrNull(1)?.toDoubleOrNull() ?: 0.0
        )
    }

    // Update stock list and calculate change
    private fun updateStock(update: StockUpdate) {
        val updated = _stocks.value.map {
            if (it.symbol == update.symbol) {
                val change = update.price - it.price
                it.copy(price = update.price, change = change)
            } else it
        }.sortedByDescending { it.price }

        _stocks.value = updated
    }
}
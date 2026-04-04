package com.zohaib.pricetracker.di

import com.zohaib.pricetracker.data.remote.websocket.StockWebSocketService
import com.zohaib.pricetracker.data.remote.websocket.StockWebSocketServiceImpl
import com.zohaib.pricetracker.data.repository.StockRepositoryImpl
import com.zohaib.pricetracker.domain.repository.StockRepository
import com.zohaib.pricetracker.domain.usecase.ObserveConnectionStatusUseCase
import com.zohaib.pricetracker.domain.usecase.ObserveStocksUseCase
import com.zohaib.pricetracker.domain.usecase.StartStockStreamUseCase
import com.zohaib.pricetracker.domain.usecase.StopStockStreamUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWebSocketService(): StockWebSocketService {
        return StockWebSocketServiceImpl()
    }

    @Provides
    @Singleton
    fun provideRepository(
        service: StockWebSocketService
    ): StockRepository {
        return StockRepositoryImpl(service)
    }

    @Provides
    fun provideObserveStocksUseCase(repo: StockRepository) =
        ObserveStocksUseCase(repo)

    @Provides
    fun provideStartUseCase(repo: StockRepository) =
        StartStockStreamUseCase(repo)

    @Provides
    fun provideStopUseCase(repo: StockRepository) =
        StopStockStreamUseCase(repo)

    @Provides
    fun provideObserveConnectionStatusUseCase(repo: StockRepository) =
        ObserveConnectionStatusUseCase(repo)
}
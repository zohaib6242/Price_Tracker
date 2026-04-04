package com.zohaib.pricetracker.presentation.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zohaib.pricetracker.presentation.components.StockItem

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            FeedTopBar(
                isRunning = state.isRunning,
                isConnected = state.isConnected,
                onToggle = { viewModel.toggle() }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(state.stocks, key = { it.symbol }) { stock ->
                StockItem(
                    stock = stock,
                    onClick = {
                        navController.navigate("details/${stock.symbol}")
                    }
                )
            }
        }
    }
}
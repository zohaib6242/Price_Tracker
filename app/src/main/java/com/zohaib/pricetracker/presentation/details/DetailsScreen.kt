package com.zohaib.pricetracker.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val stock by viewModel.stock.collectAsState()

    stock?.let {

        val color = when {
            it.change > 0 -> Color.Green
            it.change < 0 -> Color.Red
            else -> Color.Gray
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(it.symbol) }
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = it.symbol,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$${"%.2f".format(it.price)}",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (it.change >= 0)
                        "↑ ${"%.2f".format(it.change)}"
                    else
                        "↓ ${"%.2f".format(it.change)}",
                    color = color,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = getStockDescription(it.symbol),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.zohaib.pricetracker.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zohaib.pricetracker.domain.model.Stock

@Composable
fun StockItem(
    stock: Stock,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = when {
            stock.change > 0 -> Color(0xFFE8F5E9)
            stock.change < 0 -> Color(0xFFFFEBEE)
            else -> Color.Transparent
        },
        animationSpec = tween(500)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = stock.symbol,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium
            )

            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "$${"%.2f".format(stock.price)}",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                val (arrow, color) = when {
                    stock.change > 0 -> "↑" to Color.Green
                    stock.change < 0 -> "↓" to Color.Red
                    else -> "-" to Color.Gray
                }

                Text(
                    text = "$arrow ${"%.2f".format(stock.change)}",
                    color = color,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
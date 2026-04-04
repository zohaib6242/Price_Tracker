package com.zohaib.pricetracker.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedTopBar(
    isRunning: Boolean,
    isConnected: Boolean,
    onToggle: () -> Unit
) {
    TopAppBar(
        title = { Text("Stock Tracker") },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp)
            ) {
                val color = if (isConnected) Color.Green else Color.Red

                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(color, CircleShape)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = if (isConnected) "Connected" else "Disconnected",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        actions = {
            TextButton(onClick = onToggle) {
                Text(if (isRunning && isConnected) "Stop" else "Start")
            }
        }
    )
}
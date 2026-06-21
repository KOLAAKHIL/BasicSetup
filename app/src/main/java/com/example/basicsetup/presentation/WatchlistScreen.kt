package com.example.basicsetup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.basicsetup.domin.model.Stock

@Composable
fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WatchlistContent(
        uiState = uiState,
        onRefresh = viewModel::refreshWatchlist
    )
}

@Composable
fun WatchlistContent(
    uiState: WatchlistUiState,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Watchlist",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        when {
            uiState.isLoading && uiState.stocks.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            uiState.stocks.isEmpty() && uiState.errorMessage != null -> {
                Text(
                    text = "Unable to load watchlist",
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                Button(
                    onClick = onRefresh,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(if (uiState.isRefreshing) "Refreshing..." else "Refresh")
                }

                LazyColumn {
                    items(uiState.stocks) { stock ->
                        StockRow(stock = stock)
                    }
                }

                uiState.errorMessage?.let {
                    Text(
                        text = "Showing cached data",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StockRow(stock: Stock) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = stock.symbol,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stock.name,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(text = "$${stock.price}")
            Text(text = "${stock.changePercent}%")
        }
    }
}

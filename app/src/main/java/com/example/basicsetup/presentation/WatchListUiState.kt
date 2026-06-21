package com.example.basicsetup.presentation

import com.example.basicsetup.domin.model.Stock

data class WatchlistUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val errorMessage: String? = null
)
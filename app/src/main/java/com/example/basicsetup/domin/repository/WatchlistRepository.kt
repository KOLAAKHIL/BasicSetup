package com.example.basicsetup.domin.repository

import com.example.basicsetup.domin.model.Stock
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    fun observeWatchlist(): Flow<List<Stock>>
    suspend fun refreshWatchlist(): Result<Unit>
}
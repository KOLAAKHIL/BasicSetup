package com.example.basicsetup.data.repository

import com.example.basicsetup.data.local.StockDao
import com.example.basicsetup.data.mapper.toDomain
import com.example.basicsetup.data.mapper.toEntity
import com.example.basicsetup.data.remote.StockApi
import com.example.basicsetup.domin.model.Stock
import com.example.basicsetup.domin.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao
) : WatchlistRepository {

    override fun observeWatchlist(): Flow<List<Stock>> {
        return dao.observeStocks()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun refreshWatchlist(): Result<Unit> {
        return try {
            val remoteStocks = api.getWatchlist()
            dao.clearStocks()
            dao.insertStocks(remoteStocks.map { it.toEntity() })
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

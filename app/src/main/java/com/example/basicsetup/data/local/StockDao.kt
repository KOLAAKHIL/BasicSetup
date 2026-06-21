package com.example.basicsetup.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Query("SELECT * FROM stocks")
    fun observeStocks(): Flow<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(stocks: List<StockEntity>)

    @Query("DELETE FROM stocks")
    suspend fun clearStocks()

}
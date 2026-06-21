package com.example.basicsetup.data.remote

import retrofit2.http.GET

interface StockApi {
    @GET("watchlist")
    suspend fun getWatchlist(): List<StockDto>
}
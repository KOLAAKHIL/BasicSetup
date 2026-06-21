package com.example.basicsetup.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class StockDto (
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent: Double
)

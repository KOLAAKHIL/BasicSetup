package com.example.basicsetup.domin.model

data class Stock (
    val symbol: String,
    val name: String,
    val price: Double,
    val changePercent: Double
)
package com.example.basicsetup.data.mapper

import com.example.basicsetup.data.local.StockEntity
import com.example.basicsetup.data.remote.StockDto
import com.example.basicsetup.domin.model.Stock

fun StockDto.toEntity(): StockEntity {
    return StockEntity(
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
    )
}

fun StockEntity.toDomain(): Stock {
    return Stock(
        symbol = symbol,
        name = name,
        price = price,
        changePercent = changePercent,
    )
}

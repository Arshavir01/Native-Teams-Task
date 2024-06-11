package com.nativeteams.common.domain.repository

import com.nativeteams.common.domain.models.StockListItem

interface StocksRepository {
    suspend fun loadStockData(): List<StockListItem>
}
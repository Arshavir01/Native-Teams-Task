package com.nativeteams.common.domain.repository

import com.nativeteams.common.domain.models.StockData
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.utils.CustomResponse
import retrofit2.Response

interface StocksRepository {
    suspend fun loadStockData(): StockData
}
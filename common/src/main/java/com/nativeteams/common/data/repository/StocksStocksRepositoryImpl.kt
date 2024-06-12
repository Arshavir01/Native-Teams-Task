package com.nativeteams.common.data.repository

import com.nativeteams.common.data.network.NetworkApiService
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.utils.Constants

class StocksStocksRepositoryImpl (private val apiService: NetworkApiService) :
    StocksRepository {

    override suspend fun loadStockData(): List<StockListItem> {
        val response = apiService.getStockData(Constants.KEY, Constants.QUERY_REGION)
        return response.body()?.marketSummaryAndSparkResponse?.result
            ?: emptyList()
    }
}

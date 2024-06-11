package com.nativeteams.common.data.repository

import android.util.Log
import com.nativeteams.common.data.network.NetworkApiService
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.utils.Constants
import javax.inject.Inject

class StocksStocksRepositoryImpl (private val apiService: NetworkApiService) :
    StocksRepository {

    override suspend fun loadStockData(): List<StockListItem> {
        val response = apiService.getStockData(Constants.KEY, Constants.QUERY_REGION)
        return response.body()?.marketSummaryAndSparkResponse?.result
            ?: emptyList()
    }
}

package com.nativeteams.common.data.repository

import com.nativeteams.common.data.network.NetworkApiService
import com.nativeteams.common.domain.models.StockData
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.utils.Constants
import com.nativeteams.common.utils.CustomResponse
import retrofit2.Response

class StocksStocksRepositoryImpl (private val apiService: NetworkApiService) :
    StocksRepository {
    override suspend fun loadStockData(): StockData {
        return apiService.getStockData(Constants.KEY, Constants.QUERY_REGION)
    }
}

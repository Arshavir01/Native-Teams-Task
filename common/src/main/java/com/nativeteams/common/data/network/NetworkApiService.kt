package com.nativeteams.common.data.network

import com.nativeteams.common.domain.models.StockData
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.models.StockResult
import com.nativeteams.common.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkApiService {
    @GET(Constants.END_POINT_SUMMARY)
    suspend fun getStockData(
        @Header("x-rapidapi-key") key: String,
        @Query("region") region: String
    ): Response<StockData>
}
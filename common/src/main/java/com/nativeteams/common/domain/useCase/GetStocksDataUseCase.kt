package com.nativeteams.common.domain.useCase

import android.util.Log
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.utils.CustomResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStocksDataUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {
    operator fun invoke(): Flow<CustomResponse<List<StockListItem>>> = flow{
        try {
             emit(CustomResponse.Loading())
             val stockList = stocksRepository.loadStockData().marketSummaryAndSparkResponse.result
             emit(CustomResponse.Success(stockList))
        } catch (e: HttpException) {
              emit(CustomResponse.Error(e.message()))
        } catch (e: IOException) {
            emit(CustomResponse.Error(e.message ?: "Couldn't rich server, check your internet connection"))
        }
    }
}

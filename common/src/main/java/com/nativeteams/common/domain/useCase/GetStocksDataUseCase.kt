package com.nativeteams.common.domain.useCase

import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GetStocksDataUseCase @Inject constructor(
    private val stocksRepository: StocksRepository
) {
    private val _loading = MutableStateFlow<Status>(Status.Unknown)
    val loading = _loading.asStateFlow()

    private val _stocksListFlow = MutableStateFlow<List<StockListItem>>(emptyList())
    val stocksListFlow = _stocksListFlow.asStateFlow()

    suspend operator fun invoke() {
        try {
            _loading.emit(Status.Loading)
            val stockList = stocksRepository.loadStockData()
            _stocksListFlow.emit(stockList)
            _loading.emit(Status.Success)
        } catch (e: Exception) {
            _loading.emit(Status.Error(e.message ?: "Error"))
        }
    }
}

sealed class Status {
    object Unknown : Status()
    object Loading : Status()
    object Success : Status()
    data class Error(val message: String) : Status()
}
package com.nativeteams.stocksscreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.repository.StocksRepository
import com.nativeteams.common.domain.useCase.GetStocksDataUseCase
import com.nativeteams.common.domain.useCase.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val getStocksDataUseCase: GetStocksDataUseCase
): ViewModel() {
    init {
        getStockList()
    }

    val viewState = combine(
        getStocksDataUseCase.stocksListFlow,
        getStocksDataUseCase.loading
    ){ stockListItems, status ->
        return@combine StocksViewSate(stockListItems, status)
    }.asLiveData()

    fun getStockList(){
        viewModelScope.launch {
            getStocksDataUseCase.invoke()
        }
    }
}

data class StocksViewSate(
    val stocksList: List<StockListItem>,
    val loading: Status
)
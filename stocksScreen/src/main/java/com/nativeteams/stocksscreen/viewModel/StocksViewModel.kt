package com.nativeteams.stocksscreen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.domain.useCase.GetStocksDataUseCase
import com.nativeteams.common.utils.CustomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val getStocksDataUseCase: GetStocksDataUseCase,
): ViewModel() {

    private val _viewState = MutableStateFlow(StocksViewSate())
     val viewState = _viewState.asStateFlow()

    init {
        getStockList()
    }

    fun getStockList() {
        getStocksDataUseCase().onEach { result ->
            when (result) {
                is CustomResponse.Success -> {
                    _viewState.value =
                        StocksViewSate(stocksList = result.data ?: emptyList())
                }

                is CustomResponse.Error -> {
                    _viewState.value = StocksViewSate(
                        error = result.message ?: "An unexpected error",
                    )
                }

                is CustomResponse.Loading -> {
                     _viewState.value = StocksViewSate(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class StocksViewSate(
    val stocksList: List<StockListItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
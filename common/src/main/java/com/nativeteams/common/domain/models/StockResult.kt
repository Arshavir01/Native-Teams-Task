package com.nativeteams.common.domain.models

data class StockResult(
    val result: List<StockListItem>,
    val error: String?
)
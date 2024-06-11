package com.nativeteams.common.domain.models

data class StockListItem(
    val fullExchangeName: String,
    val symbol: String,
    val spark: Spark
)

data class Spark(
    val previousClose: Double,
    val close: List<Double>?
)


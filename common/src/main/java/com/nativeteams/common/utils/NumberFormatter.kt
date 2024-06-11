package com.nativeteams.common.utils
import java.text.DecimalFormat

object NumberFormatter {
    fun formattingNumbers(number: Number): String {
        val decimalFormat = DecimalFormat("#,###.00")
        return decimalFormat.format(number)
    }

    fun formattingDiffNumbers(number: Number): String {
        val decimalFormat = DecimalFormat("0.00")
        return decimalFormat.format(number)
    }

    fun calculateDiffPercentage(diffValue: Double, lastCloseNumber: Double): String{
        val percentage = (100 * diffValue) / lastCloseNumber
        return formattingDiffNumbers(percentage)
    }
}
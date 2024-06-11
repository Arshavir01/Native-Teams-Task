package com.nativeteams.stocksscreen

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nativeteams.common.domain.models.StockListItem
import com.nativeteams.common.utils.Constants
import com.nativeteams.common.utils.NumberFormatter
import com.nativeteams.common.utils.NumberFormatter.formattingDiffNumbers
import com.nativeteams.common.utils.NumberFormatter.formattingNumbers
import com.nativeteams.stocksscreen.databinding.StockItemBinding
import javax.inject.Inject

class StockListViewAdapter @Inject constructor() :
    ListAdapter<StockListItem, RecyclerView.ViewHolder>(
        stocksDiffCallback
    ) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as StockItemViewHolder).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        StockItemViewHolder(
            StockItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    companion object {
        val stocksDiffCallback = object : DiffUtil.ItemCallback<StockListItem>() {
            override fun areItemsTheSame(
                oldItem: StockListItem,
                newItem: StockListItem
            ): Boolean {
                return oldItem.symbol == newItem.symbol

            }

            override fun areContentsTheSame(
                oldItem: StockListItem,
                newItem: StockListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class StockItemViewHolder(private val binding: StockItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banksListItem: StockListItem) {
            binding.stockName.text = banksListItem.symbol
            binding.stockFullName.text = banksListItem.fullExchangeName
            val lastCloseNumber = banksListItem.spark.close?.lastOrNull()
            val previousNumber = banksListItem.spark.previousClose

            if (lastCloseNumber != null) {
                binding.stockValue.text = formattingNumbers(lastCloseNumber)
                val diff = lastCloseNumber - previousNumber
                setStockDiffValue(diff, binding.stockValueDiff, binding.root.context)
                setStockDiffPercentage(
                    diff,
                    lastCloseNumber,
                    binding.stockValueDiffPercentage,
                    binding.root.context
                )
            } else {
                setNullValues(
                    binding.stockValue,
                    binding.stockValueDiff,
                    binding.stockValueDiffPercentage
                )
            }
        }
    }

    private fun setStockDiffValue(
        diff: Double,
        diffValueText: TextView,
        context: Context
    ) {
        val diffDoubleValue = formattingDiffNumbers(diff).toDouble()

        if (diffDoubleValue < 0) {
            diffValueText.setTextColor(context.resources.getColor(R.color.red, null))
            diffValueText.text = formattingDiffNumbers(diff)
        } else if (diffDoubleValue > 0) {
            diffValueText.setTextColor(context.resources.getColor(R.color.green, null))
            val concatPlusSign = "+${formattingDiffNumbers(diff)}"
            diffValueText.text = concatPlusSign
        } else {
            diffValueText.text = formattingDiffNumbers(diff)
            diffValueText.setTextColor(context.resources.getColor(R.color.gray, null))
        }
    }

    private fun setStockDiffPercentage(
        diff: Double,
        lastCloseNumber: Double,
        diffPercentageText: TextView,
        context: Context
    ) {
        val diffDoubleValue = formattingDiffNumbers(diff).toDouble()
        if (diffDoubleValue < 0) {
            diffPercentageText.setTextColor(context.resources.getColor(R.color.red, null))
            val percentage =
                NumberFormatter.calculateDiffPercentage(diff, lastCloseNumber)
            val finalString = "($percentage%)"
            diffPercentageText.text = finalString
        } else if (diffDoubleValue > 0) {
            diffPercentageText.setTextColor(context.resources.getColor(R.color.green, null))
            val percentage =
                NumberFormatter.calculateDiffPercentage(diff, lastCloseNumber)
            val finalString = "(+$percentage%)"
            diffPercentageText.text = finalString
        } else {
            diffPercentageText.setTextColor(context.resources.getColor(R.color.gray, null))
        }
    }

    private fun setNullValues(
        stockVale: TextView,
        stockDiffValue: TextView,
        stockDiffPercentage: TextView
    ) {
        stockVale.text = Constants.NULL_VALUE
        stockDiffValue.text = Constants.NULL_VALUE
        stockDiffPercentage.text = Constants.NULL_VALUE
    }
}
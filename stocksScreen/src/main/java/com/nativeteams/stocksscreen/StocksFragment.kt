package com.nativeteams.stocksscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeteams.stocksscreen.databinding.FragmentStocksBinding
import com.nativeteams.stocksscreen.viewModel.StocksViewModel
import com.nativeteams.stocksscreen.viewModel.StocksViewSate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksFragment : Fragment() {
    private lateinit var binding: FragmentStocksBinding
    private lateinit var stockAdapter: StockListViewAdapter
    private val viewModel: StocksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSwipeRefresh()

        collectLatestLifecycleFlow(viewModel.viewState) { stocksViewSate ->
            binding.progressBar.isVisible = stocksViewSate.isLoading
            val stocksList = stocksViewSate.stocksList
            stockAdapter.submitList(stocksList)
        }
    }

    private fun setSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            viewModel.getStockList()
        }
    }

    private fun setRecyclerView() {
        stockAdapter = StockListViewAdapter()
        binding.stockList.layoutManager = LinearLayoutManager(requireContext())
        binding.stockList.adapter = stockAdapter
    }

}

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit){
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
           flow.collectLatest(collect)
        }
    }
}
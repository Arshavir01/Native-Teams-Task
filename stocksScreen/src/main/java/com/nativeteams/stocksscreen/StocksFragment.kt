package com.nativeteams.stocksscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nativeteams.common.domain.useCase.Status
import com.nativeteams.stocksscreen.databinding.FragmentStocksBinding
import com.nativeteams.stocksscreen.viewModel.StocksViewModel
import com.nativeteams.stocksscreen.viewModel.StocksViewSate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StocksFragment : Fragment() {
    private lateinit var binding: FragmentStocksBinding
    private lateinit var viewModel: StocksViewModel
    private lateinit var stockAdapter: StockListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[StocksViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setSwipeRefresh()
        viewModel.viewState.observe(viewLifecycleOwner, ::bindViewState)
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

    private fun bindViewState(viewState: StocksViewSate) {
        val status = viewState.loading
        val stocksList = viewState.stocksList

        stockAdapter.submitList(stocksList)

        when (status) {
            Status.Loading -> {
                binding.progressBar.isVisible = true
            }

            is Status.Error -> {
                binding.infoText.text = status.message
                binding.progressBar.isVisible = false
            }

            Status.Success -> {
                binding.infoText.text = getString(R.string.empty_list)
                binding.infoText.isVisible = stocksList.isEmpty()
                binding.progressBar.isVisible = false
            }

            else -> {
                binding.progressBar.isVisible = false
                binding.infoText.isVisible = false
            }
        }
    }
}
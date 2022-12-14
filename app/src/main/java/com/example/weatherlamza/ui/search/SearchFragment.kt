package com.example.weatherlamza.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.distinctUntilChanged
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentSearchBinding
import com.example.weatherlamza.ui.search.adapters.RecentSearchesAdapter
import com.example.weatherlamza.ui.weather.adapters.DailyWeatherForecastAdapter
import com.example.weatherlamza.utils.extensions.gone
import com.example.weatherlamza.utils.extensions.observeState
import com.example.weatherlamza.utils.extensions.populateWithLocationData
import com.example.weatherlamza.utils.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView.OnQueryTextListener {

    enum class State {
        RECENT, RESULT
    }

    private val searchViewModel by viewModel<SearchViewModel>()
    private val recentSearchesAdapter by lazy { RecentSearchesAdapter() }
    private val dailyForecastAdapter by lazy { DailyWeatherForecastAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
        setListeners()
        setObservers()
    }

    private fun setUI() {
        with(binding) {
            search.setOnQueryTextListener(this@SearchFragment)
            recentContainer.recentSearchedItems.adapter = recentSearchesAdapter
            resultContainer.dailyForecast.adapter = dailyForecastAdapter
        }
    }

    private fun setObservers() {
        with(searchViewModel) {
            searchState.distinctUntilChanged()
                .observe(viewLifecycleOwner) {
                    onStateChanged(it)
                }

            recentlySearchedQueries.observe(viewLifecycleOwner) {
                recentSearchesAdapter.recentlySearchedQueries = it.map { it.query }
            }

            weather.observe(viewLifecycleOwner) {
                binding.resultContainer.populateWithLocationData(it, requireContext())
            }

            forecast.observe(viewLifecycleOwner) {
                dailyForecastAdapter.dailyWeatherForecast = it
            }

            searchQueryState.observeState(viewLifecycleOwner, this@SearchFragment) {}
        }
    }

    private fun setListeners() {
        with(binding) {
            search.setOnCloseListener {
                onStateChanged(State.RECENT)
                true
            }
            recentSearchesAdapter.onQuerySelected = { query ->
                binding.search.setQuery(query, true)
            }

            recentSearchesAdapter.onQueryRemoved = { query ->
                searchViewModel.removeQuery(query)
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.search.clearFocus()
        query?.let {
            searchViewModel.setSearchQuery(it)
            searchViewModel.getWeatherForQuery(it)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchViewModel.searchQuery.value = null
        query?.let { searchViewModel.setLiveSearchQuery(it) }
        return true
    }

    private fun onStateChanged(state: State) {
        when (state) {
            State.RECENT -> {
                binding.resultContainer.weatherContent.gone()
                binding.recentContainer.container.visible()
            }
            State.RESULT -> {
                binding.recentContainer.container.gone()
                binding.resultContainer.weatherContent.visible()
            }
        }
    }
}

package com.example.weatherlamza.ui

import android.os.Bundle
import android.view.View
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentWeatherCollapsingBinding
import com.example.weatherlamza.ui.search.adapters.RecentSearchesAdapter
import com.example.weatherlamza.utils.extensions.gone
import com.example.weatherlamza.utils.extensions.visible
import kotlin.math.abs

class TestFragment :
    BaseFragment<FragmentWeatherCollapsingBinding>(FragmentWeatherCollapsingBinding::inflate) {

    private val recentSearchesAdapter by lazy { RecentSearchesAdapter() }
    private val mockListSearches = listOf(
        "bla",
        "bla",
        "ble",
        "blo",
        "blu",
        "bli",
        "bleh",
        "hme",
        "jesus",
        "loves",
        "you",
        "do",
        "you",
        "love",
        "him",
        "and",
        "why",
        "not"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()

        binding.topBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            val isCollapsed = abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0

            if (isCollapsed) {
                binding.toolbar.visible()
            } else {
                binding.toolbar.gone()
            }
        }
    }


    private fun setUI() {
        with(binding) {
//            recentSearches.adapter = recentSearchesAdapter
            recentSearchesAdapter.recentlySearchedQueries = mockListSearches
        }
    }

}



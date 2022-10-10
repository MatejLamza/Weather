package com.example.weatherlamza.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _searchState = MutableLiveData(SearchFragment.State.RECENT)
    val searchState: LiveData<SearchFragment.State> = _searchState

    val searchQuery = MutableLiveData<String>()

    /**
     * Extracted to separate variable to not trigger [filteredUsers] and [filteredPositions].
     * Since [RecentSearchFragment] is navigating to next screen as soon as user starts typing
     * query that we want to remember and prefill [SearchFragment] with.
     */
    val liveSearchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        searchQuery.value = query
        searchStateChanged(SearchFragment.State.RESULT)
    }

    fun setLiveSearchQuery(query: String) {
        if (query.isNotBlank() && liveSearchQuery.value != query) {
            liveSearchQuery.value = query
            searchStateChanged(SearchFragment.State.RECENT)
        }
    }

    fun searchStateChanged(newState: SearchFragment.State) {
        when (newState) {
            SearchFragment.State.RECENT -> {
                if (searchQuery.value == null)
                    _searchState.value = newState
            }
            else -> _searchState.value = newState
        }
    }
}

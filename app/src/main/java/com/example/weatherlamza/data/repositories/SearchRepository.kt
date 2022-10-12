package com.example.weatherlamza.data.repositories

import com.example.weatherlamza.data.local.entity.RecentSearchesEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getRecentSearches(): Flow<List<RecentSearchesEntity>>

    suspend fun removeQuery(query: String)

    suspend fun clearAllQueries()
}

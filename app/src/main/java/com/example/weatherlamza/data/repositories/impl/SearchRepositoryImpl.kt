package com.example.weatherlamza.data.repositories.impl

import com.example.weatherlamza.data.local.dao.SearchDAO
import com.example.weatherlamza.data.local.entity.RecentSearchesEntity
import com.example.weatherlamza.data.repositories.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SearchRepositoryImpl(
    private val searchDAO: SearchDAO,
    private val coroutineDispatcher: CoroutineDispatcher = IO
) : SearchRepository {

    override fun getRecentSearches(): Flow<List<RecentSearchesEntity>> =
        searchDAO.getRecentSearchQueries()

    override suspend fun removeQuery(query: String) {
        withContext(coroutineDispatcher) {
            searchDAO.deleteUserQuery(query)
        }
    }

    override suspend fun clearAllQueries() {
        withContext(coroutineDispatcher) {
            searchDAO.deleteAllQueries()
        }
    }

}

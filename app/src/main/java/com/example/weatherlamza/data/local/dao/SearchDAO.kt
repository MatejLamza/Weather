package com.example.weatherlamza.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherlamza.data.local.entity.RecentSearchesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDAO {

    @Query("SELECT * FROM recent_queries")
    fun getRecentSearchQueries(): Flow<List<RecentSearchesEntity>>

    @Insert
    fun insertRecentSearchQuery(recentQuery: RecentSearchesEntity)

    @Query("DELETE FROM recent_queries WHERE `query` = :query")
    fun deleteUserQuery(query: String)

    @Query("DELETE FROM recent_queries")
    fun deleteAllQueries()
}

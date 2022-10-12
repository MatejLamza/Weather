package com.example.weatherlamza.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_queries")
data class RecentSearchesEntity(
    /**
     * [query] is a [PrimaryKey] so we can't have two same queries in recent searches.
     */
    @PrimaryKey
    val query: String
)


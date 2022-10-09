package com.example.weatherlamza.data.local

import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    suspend fun changeNotificationPermission(arePermissionsEnabled: Boolean)

    fun getNotificationPermissions(): Flow<Boolean>

    suspend fun invalidate()
}

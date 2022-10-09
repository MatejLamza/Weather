package com.example.weatherlamza.data.local

import kotlinx.coroutines.flow.Flow

class SessionRepositoryImpl(private val sessionPrefs: SessionPrefs) : SessionRepository {

    override suspend fun changeNotificationPermission(arePermissionsEnabled: Boolean) {
        sessionPrefs.setPermissionForNotifications(arePermissionsEnabled)
    }

    override fun getNotificationPermissions(): Flow<Boolean> =
        sessionPrefs.observePermissionForNotifications()


    override suspend fun invalidate() {
        sessionPrefs.clear()
    }
}

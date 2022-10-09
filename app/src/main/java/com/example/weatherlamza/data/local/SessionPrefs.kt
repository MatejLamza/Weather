package com.example.weatherlamza.data.local

import android.content.SharedPreferences
import com.example.weatherlamza.utils.extensions.observeBoolean
import com.example.weatherlamza.utils.extensions.setBoolean


private const val PERMISSION_NOTIFICATION_KEY = "permission_notification_key"

class SessionPrefs(private val sharedPreferences: SharedPreferences) {

    suspend fun setPermissionForNotifications(value: Boolean) =
        sharedPreferences.setBoolean(PERMISSION_NOTIFICATION_KEY, value)

    suspend fun observePermissionForNotifications() =
        sharedPreferences.observeBoolean(PERMISSION_NOTIFICATION_KEY)

}

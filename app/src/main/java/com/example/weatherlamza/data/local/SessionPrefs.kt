package com.example.weatherlamza.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.weatherlamza.utils.extensions.observeBoolean
import com.example.weatherlamza.utils.extensions.setBoolean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private const val PERMISSION_NOTIFICATION_KEY = "permission_notification_key"

class SessionPrefs(private val sharedPreferences: SharedPreferences) {

    suspend fun setPermissionForNotifications(value: Boolean) =
        sharedPreferences.setBoolean(PERMISSION_NOTIFICATION_KEY, value)

    fun observePermissionForNotifications() =
        sharedPreferences.observeBoolean(PERMISSION_NOTIFICATION_KEY)


    suspend fun clear() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit(commit = true) {
                sharedPreferences.all.keys.forEach {
                    remove(it)
                }
            }
        }
    }
}

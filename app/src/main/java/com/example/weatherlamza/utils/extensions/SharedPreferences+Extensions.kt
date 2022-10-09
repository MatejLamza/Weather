package com.example.weatherlamza.utils.extensions

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext


suspend fun SharedPreferences.setBoolean(key: String, value: Boolean, commit: Boolean = true) {
    withContext(IO) {
        edit(commit = commit) { putBoolean(key, value) }
    }
}

suspend fun SharedPreferences.getBoolean(key: String) = withContext(IO) {
    getBoolean(key, false)
}

fun SharedPreferences.observeBoolean(key: String): Flow<Boolean> {
    return channelFlow {
        trySend(getBoolean(key, false))
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            if (key == changedKey) {
                trySend(getBoolean(key, false))
            }
        }
        registerOnSharedPreferenceChangeListener(listener)
        awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
    }.flowOn(IO)
}

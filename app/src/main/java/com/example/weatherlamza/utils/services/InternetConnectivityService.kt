package com.example.weatherlamza.utils.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.weatherlamza.common.state.ConnectivityState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class InternetConnectivityService(context: Context) {

    private val connectivityManager by lazy {
        kotlin.runCatching {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }.getOrNull()
    }

    private val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    val networkStatus = callbackFlow<ConnectivityState> {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                trySend(ConnectivityState.Unavailable)
            }

            override fun onAvailable(network: Network) {
                trySend(ConnectivityState.Available)
            }

            override fun onLost(network: Network) {
                trySend(ConnectivityState.Unavailable)
            }
        }

        connectivityManager?.registerNetworkCallback(request, networkCallback)

        awaitClose { connectivityManager?.unregisterNetworkCallback(networkCallback) }
    }
}

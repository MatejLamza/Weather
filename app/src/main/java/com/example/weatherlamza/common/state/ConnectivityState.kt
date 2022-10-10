package com.example.weatherlamza.common.state

sealed class ConnectivityState {
    object Unavailable : ConnectivityState()
    object Available : ConnectivityState()
}


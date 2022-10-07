package com.example.weatherlamza.common.state

sealed class State {
    data class Done(val hasData: Boolean? = null) : State()
    data class Error(val throwable: Throwable?) : State()
    object Loading : State()
}

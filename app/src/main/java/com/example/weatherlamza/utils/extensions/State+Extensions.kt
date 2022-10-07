package com.example.weatherlamza.utils.extensions

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherlamza.common.state.State
import com.example.weatherlamza.common.state.State.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun exceptionHandler(onError: ((Throwable) -> Unit)) =
    CoroutineExceptionHandler { _, exception ->
        Log.e("ViewModel", "Error in ViewModel", exception)
        onError(exception)
    }

fun exceptionHandler(data: MutableLiveData<State>? = null) =
    exceptionHandler { data?.postValue(Error(it)) }

fun ViewModel.launch(
    context: CoroutineContext = exceptionHandler(null),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) =
    viewModelScope.launch(context, start) {
        block(this)
    }

fun ViewModel.launchWithState(
    data: MutableLiveData<State>,
    onLoading: (() -> Unit)? = { data.value = Loading },
    onError: ((Throwable) -> Unit)? = { data.value = Error(it) },
    onDone: (() -> Unit)? = { data.value = Done() },
    context: CoroutineContext =
        if (onError != null) exceptionHandler(onError)
        else exceptionHandler(data),
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) = viewModelScope.launch(context, start) {
    onLoading?.invoke()
    block(this)
    onDone?.invoke()
}

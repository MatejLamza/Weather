package com.example.weatherlamza.common.base

interface View {
    fun dismissLoading()

    fun showLoading()

    fun showError(error: Throwable)
}

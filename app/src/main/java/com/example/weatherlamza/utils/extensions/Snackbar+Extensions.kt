package com.example.weatherlamza.utils.extensions

import androidx.core.content.ContextCompat
import com.example.weatherlamza.R
import com.google.android.material.snackbar.Snackbar

fun Snackbar.info() = this.apply {
    animationMode = Snackbar.ANIMATION_MODE_SLIDE
    setTextColor(ContextCompat.getColor(this.context, R.color.white))
    setActionTextColor(ContextCompat.getColor(this.context, R.color.white))
    setBackgroundTint(ContextCompat.getColor(this.context, R.color.highlight_purple))
}

fun Snackbar.error() = this.apply {
    animationMode = Snackbar.ANIMATION_MODE_SLIDE
    setTextColor(ContextCompat.getColor(this.context, R.color.white))
    setActionTextColor(ContextCompat.getColor(this.context, R.color.white))
    setBackgroundTint(ContextCompat.getColor(this.context, R.color.highlight_red))
}

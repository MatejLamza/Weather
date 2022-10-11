package com.example.weatherlamza.utils.extensions

import android.app.Activity
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


fun Activity.infoSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(this, view, message, duration).info()

fun Activity.errorSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(this, view, message, duration).error()

fun Activity.infoSnackBar(
    view: View, @StringRes messageRes: Int,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = this.infoSnackBar(view, getString(messageRes), duration)

fun Activity.errorSnackBar(
    view: View, @StringRes messageRes: Int,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = this.errorSnackBar(view, getString(messageRes), duration)

fun AppCompatActivity.hideToolbar() {
    if (supportActionBar != null) supportActionBar!!.hide()
}

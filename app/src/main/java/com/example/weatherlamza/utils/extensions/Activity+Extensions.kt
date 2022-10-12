package com.example.weatherlamza.utils.extensions

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX


fun Activity.infoSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(this, view, message, duration).info()

fun Activity.errorSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(this, view, message, duration).error()

fun AppCompatActivity.getNavHostFragment(hostFragmentId: Int): NavHostFragment =
    supportFragmentManager.findFragmentById(hostFragmentId) as NavHostFragment

fun AppCompatActivity.getNavController(id: Int): NavController =
    getNavHostFragment(id).navController


fun AppCompatActivity.checkPermissions(
    permissions: List<String>,
    requestCode: Int? = null,
    onSuccess: () -> Unit,
    onError: () -> Unit = {
        ActivityCompat.requestPermissions(
            this,
            permissions.toTypedArray(),
            requestCode!!
        )
    },
) {
    PermissionX.init(this)
        .permissions(permissions)
        .request { allGranted, grantedList, deniedList ->
            if (allGranted) onSuccess.invoke()
            else onError.invoke()
        }
}

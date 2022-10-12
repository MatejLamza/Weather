package com.example.weatherlamza.utils.extensions

import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.permissionx.guolindev.PermissionX

fun Fragment.checkPermissions(
    permissions: List<String>,
    requestCode: Int? = null,
    onSuccess: () -> Unit,
    onError: () -> Unit = {
        ActivityCompat.requestPermissions(
            requireActivity(),
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


fun Fragment.infoSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(requireContext(), view, message, duration).info()

fun Fragment.errorSnackBar(
    view: View, message: String,
    @BaseTransientBottomBar.Duration duration: Int = Snackbar.LENGTH_SHORT
) = Snackbar.make(requireContext(), view, message, duration).error()

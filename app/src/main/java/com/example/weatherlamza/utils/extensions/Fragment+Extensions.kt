package com.example.weatherlamza.utils.extensions

import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
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

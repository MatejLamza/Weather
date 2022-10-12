package com.example.weatherlamza.utils.extensions

import com.example.weatherlamza.utils.LocationWithPermission

val LocationWithPermission.areNotificationsEnabled
    get() = second

val LocationWithPermission.currentLocation
    get() = first

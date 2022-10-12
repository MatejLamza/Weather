package com.example.weatherlamza

import android.location.Location
import androidx.lifecycle.*
import com.example.weatherlamza.data.local.SessionPrefs
import com.example.weatherlamza.utils.LocationWithPermission

class MainViewModel(sessionPrefs: SessionPrefs) : ViewModel() {

    val currentLocation = MutableLiveData<Location>()

    val locationWithPermission = object : MediatorLiveData<LocationWithPermission>() {
        init {
            addSource(currentLocation.distinctUntilChanged()) {
                process(it, sessionPrefs.observePermissionForNotifications().asLiveData().value)
            }
            addSource(
                sessionPrefs.observePermissionForNotifications().asLiveData().distinctUntilChanged()
            ) {
                process(areNotificationsEnabled = it)
            }
        }

        private fun process(
            location: Location? = currentLocation.value,
            areNotificationsEnabled: Boolean?
        ) {
            if (location != null && areNotificationsEnabled != null) {
                value = LocationWithPermission(location, areNotificationsEnabled)
            }
        }
    }
}

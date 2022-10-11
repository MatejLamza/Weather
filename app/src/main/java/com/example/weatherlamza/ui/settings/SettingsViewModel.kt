package com.example.weatherlamza.ui.settings

import androidx.lifecycle.ViewModel
import com.example.weatherlamza.data.local.SessionRepository
import com.example.weatherlamza.utils.extensions.launch

class SettingsViewModel(private val sessionRepository: SessionRepository) : ViewModel() {

    fun updateNotificationPermissions(areEnabled: Boolean) {
        launch {
            sessionRepository.changeNotificationPermission(areEnabled)
        }
    }
}

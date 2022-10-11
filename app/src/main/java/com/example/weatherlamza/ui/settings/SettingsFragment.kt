package com.example.weatherlamza.ui.settings

import android.os.Bundle
import android.view.View
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.databinding.FragmentSettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val settingsViewModel by viewModel<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            notificationPermissions.setOnCheckedChangeListener { _, areNotificationsEnabled ->
                settingsViewModel.updateNotificationPermissions(areNotificationsEnabled)
            }
        }
    }
}

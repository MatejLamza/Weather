package com.example.weatherlamza.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.weatherlamza.common.base.BaseFragment
import com.example.weatherlamza.data.local.SessionRepository
import com.example.weatherlamza.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val sessionRepo by inject<SessionRepository>(SessionRepository::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            enable.setOnClickListener {
                lifecycleScope.launch {
                    Log.d("aaa", "Changing notification permission to true ")
                    sessionRepo.changeNotificationPermission(true)
                    Log.d("aaa", "Done changing notification permission ")
                }
            }
            disable.setOnClickListener {
                lifecycleScope.launch {
                    Log.d(TAG, "Changing notification permissions to false: ")
                    sessionRepo.changeNotificationPermission(false)
                    Log.d(TAG, "Done changing notifs: ")
                }
            }
        }
    }
}

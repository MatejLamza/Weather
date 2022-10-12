package com.example.weatherlamza.di.modules

import androidx.fragment.app.FragmentManager
import com.example.weatherlamza.common.base.ProgressDialog
import org.koin.dsl.module

val dialogModule = module {
    factory { (manager: FragmentManager) -> ProgressDialog(manager) }
}

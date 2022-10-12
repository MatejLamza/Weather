package com.example.weatherlamza.utils

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup

typealias ViewBindingInflate<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

typealias LocationWithPermission = Pair<Location, Boolean>


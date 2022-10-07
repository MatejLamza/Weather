package com.example.weatherlamza.common.base

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.weatherlamza.utils.ViewBindingInflate

open class BaseFragment<VB : ViewBinding>(
    private val inflate: ViewBindingInflate<VB>,
) : Fragment(), View {

    companion object {
        const val TAG = "BaseFragment"
        val permissions = arrayListOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun dismissLoading() {
        Log.d(TAG, "dismissLoading ... ")
    }

    override fun showLoading() {
        Log.d(TAG, "Loading .... ")
    }

    override fun showError(error: Throwable) {
        Log.e(TAG, "Eror happend", error)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
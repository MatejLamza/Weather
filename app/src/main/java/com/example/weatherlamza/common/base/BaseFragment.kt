package com.example.weatherlamza.common.base

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.weatherlamza.R
import com.example.weatherlamza.navigation.NavigationViewModel
import com.example.weatherlamza.utils.ViewBindingInflate
import com.example.weatherlamza.utils.extensions.errorSnackBar
import com.example.weatherlamza.utils.extensions.gone
import com.example.weatherlamza.utils.extensions.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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

    protected val navigation by viewModel<NavigationViewModel>()
    protected val progressDialog: ProgressDialog by inject { parametersOf(childFragmentManager) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<android.view.View>(R.id.progressIndicator)?.gone()
    }

    override fun showLoading() {
        view?.findViewById<android.view.View>(R.id.progressIndicator)?.visible()
            ?: progressDialog.show()
    }

    override fun dismissLoading() {
        val indicator = view?.findViewById<android.view.View>(R.id.progressIndicator)
        if (indicator != null) {
            indicator.gone()
            AlertDialog.Builder(requireContext()).create()
        } else {
            runCatching { progressDialog.dismiss() }
        }
    }

    override fun showError(error: Throwable) {
        errorSnackBar(requireView(), error.message ?: "Unknown error").show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        runCatching { progressDialog.dismiss() }
        _binding = null
    }
}

package com.example.weatherlamza.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.weatherlamza.databinding.DialogProgressBinding
import com.example.weatherlamza.utils.extensions.visibleIf

class ProgressDialog(
    private val manager: FragmentManager,
    private val title: String? = null,
    private val message: String? = null
) : DialogFragment() {

    private lateinit var binding: DialogProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.progressMessage.visibleIf(!message.isNullOrBlank()) { text = title }
    }

    fun show(tag: String = "progress") {
        if (!isVisible) {
            show(manager, tag)
        }
    }

}

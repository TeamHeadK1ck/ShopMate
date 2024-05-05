package com.example.purchaselist_project.presentation.abstraction.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class AbstractFragment<BINDING: ViewBinding>(
    @LayoutRes contentLayoutId: Int
): Fragment(contentLayoutId) {
    private var _binding: BINDING? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    protected abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
package com.example.purchaselist_project.presentation.fragment.mainMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.FragmentMainMenuBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractFragment

class MainMenuFragment : AbstractFragment<FragmentMainMenuBinding>(R.layout.fragment_main_menu), View.OnClickListener {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainMenuBinding = FragmentMainMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            registrationText.setOnClickListener(this@MainMenuFragment)
            authorizationText.setOnClickListener(this@MainMenuFragment)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.registrationText -> {
                findNavController().navigate(R.id.action_mainMenuFragment_to_registrationFragment)
            }
            R.id.authorizationText -> {
                findNavController().navigate(R.id.action_mainMenuFragment_to_authorizationFragment)
            }
        }
    }
}
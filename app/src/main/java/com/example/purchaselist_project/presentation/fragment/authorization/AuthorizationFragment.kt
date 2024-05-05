package com.example.purchaselist_project.presentation.fragment.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.purchaselist_project.App
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.FragmentAuthorizationBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractFragment
import com.example.purchaselist_project.presentation.fragment.registration.RegistrationFragmentDirections
import com.example.purchaselist_project.utils.state.LoginAndRegistrationStatus
import kotlinx.coroutines.launch

class AuthorizationFragment : AbstractFragment<FragmentAuthorizationBinding>(R.layout.fragment_authorization), View.OnClickListener {
    private lateinit var viewModel: AuthorizationFragmentViewModel

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthorizationBinding = FragmentAuthorizationBinding.inflate(inflater, container, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (requireActivity().application as App).viewModelFactory().create(AuthorizationFragmentViewModel::class.java)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            validatorAuthorizationButton.setOnClickListener(this@AuthorizationFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validation.collect { valid->
                if(valid != null) {
                    when(valid) {
                        LoginAndRegistrationStatus.AllGood ->
                            findNavController().navigate(
                                AuthorizationFragmentDirections
                                    .actionAuthorizationFragmentToShoppingListFragment(
                                        binding.enterEmailView.text.toString()
                                    )
                            )
                        LoginAndRegistrationStatus.ErrorPassword ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка: неверный пароль!",
                                Toast.LENGTH_SHORT
                            ).show()
                        LoginAndRegistrationStatus.ErrorEmail ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка: неверный email!",
                                Toast.LENGTH_SHORT
                            ).show()
                        LoginAndRegistrationStatus.EmptyField ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка: поля входа не могут быть пустыми!",
                                Toast.LENGTH_SHORT
                            ).show()
                        else ->
                            Toast.makeText(requireContext(),
                                "Непредвиденная ошибка, попробуйте зайти позже!",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.validatorAuthorizationButton -> {
                with(binding) {
                    viewModel.validationUser(enterEmailView.text.toString(), enterPassView.text.toString())
                }
            }
        }
    }
}
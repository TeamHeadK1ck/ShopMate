package com.example.purchaselist_project.presentation.fragment.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.purchaselist_project.App
import com.example.purchaselist_project.R
import com.example.purchaselist_project.databinding.FragmentRegistrationBinding
import com.example.purchaselist_project.presentation.abstraction.ui.AbstractFragment
import com.example.purchaselist_project.presentation.fragment.shoppingList.ShoppingListFragmentDirections
import com.example.purchaselist_project.utils.state.LoginAndRegistrationStatus
import kotlinx.coroutines.launch

class RegistrationFragment : AbstractFragment<FragmentRegistrationBinding>(R.layout.fragment_registration), View.OnClickListener {
    private lateinit var viewModel: RegistrationFragmentViewModel

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding = FragmentRegistrationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (requireActivity().application as App).viewModelFactory().create(RegistrationFragmentViewModel::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.validation.collect{ validation->
                if(validation != null) {
                    when(validation) {
                        LoginAndRegistrationStatus.AllGood ->
                            findNavController().navigate(
                                RegistrationFragmentDirections
                                    .actionRegistrationFragmentToShoppingListFragment(
                                        binding.enterEmailRegistrationView.text.toString()
                                    )
                            )
                        LoginAndRegistrationStatus.EmailAlreadyExists ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка регистрации: пользователь с таким email уже существует!",
                                Toast.LENGTH_SHORT
                            ).show()
                        LoginAndRegistrationStatus.PasswordsDoNotMatch ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка регистрации: поля проверки паролей не совпадают!",
                                Toast.LENGTH_SHORT
                            ).show()
                        LoginAndRegistrationStatus.EmptyField ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка регистрации: поля регистрации не могут быть пустыми!",
                                Toast.LENGTH_SHORT
                            ).show()
                        LoginAndRegistrationStatus.NotCorrectEmail ->
                            Toast.makeText(
                                requireContext(),
                                "Ошибка регистрации: некорректный формат ввода email!",
                                Toast.LENGTH_SHORT
                            ).show()
                        else ->
                            Toast.makeText(requireContext(),
                                "Непредвиденная ошибка, попробуйте позже!",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
                }
            }
        }
        with(binding) {
            validationRegistrationButton.setOnClickListener(this@RegistrationFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.validationRegistrationButton ->
                viewModel.createUser(
                    binding.enterEmailRegistrationView.text.toString(),
                    binding.enterPassRegisterView.text.toString(),
                    binding.enterPassRegisterValidateView.text.toString()
                )
        }
    }
}
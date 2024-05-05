package com.example.purchaselist_project.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.presentation.fragment.authorization.AuthorizationFragmentViewModel
import com.example.purchaselist_project.presentation.fragment.registration.RegistrationFragmentViewModel
import com.example.purchaselist_project.presentation.fragment.shoppingList.ShoppingListFragmentViewModel

class ViewModelFactory (private val repository: DomainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        AuthorizationFragmentViewModel::class.java -> AuthorizationFragmentViewModel(repository)
        RegistrationFragmentViewModel::class.java -> RegistrationFragmentViewModel(repository)
        ShoppingListFragmentViewModel::class.java -> ShoppingListFragmentViewModel(repository)

        else -> throw IllegalStateException("unknown modelClass viewModel:$modelClass")
    } as T
}
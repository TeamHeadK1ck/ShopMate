package com.example.purchaselist_project.presentation.fragment.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.utils.state.LoginAndRegistrationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthorizationFragmentViewModel(private val repository: DomainRepository): ViewModel() {
    private val _validation = MutableStateFlow<LoginAndRegistrationStatus?>(null)
    val validation: StateFlow<LoginAndRegistrationStatus?>
        get() = _validation

    fun validationUser(email: String, pass: String) {
         viewModelScope.launch(Dispatchers.IO) {
            if(email.isEmpty() || email == "" || pass.isEmpty() || pass == "") {
                _validation.value = LoginAndRegistrationStatus.EmptyField
            }
            else {
                val user = repository.getUser(email)
                if(user.size == 1) {
                    if(user[0].pass == pass) {
                        _validation.value = LoginAndRegistrationStatus.AllGood
                    }
                    else {
                        _validation.value = LoginAndRegistrationStatus.ErrorPassword
                    }
                }
                else {
                    _validation.value = LoginAndRegistrationStatus.ErrorEmail
                }
            }
        }
    }
}
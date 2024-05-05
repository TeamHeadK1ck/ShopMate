package com.example.purchaselist_project.presentation.fragment.registration

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.purchaselist_project.domain.DomainRepository
import com.example.purchaselist_project.utils.state.LoginAndRegistrationStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationFragmentViewModel(private val repository: DomainRepository): ViewModel() {
    private val _validation = MutableStateFlow<LoginAndRegistrationStatus?>(null)
    val validation: StateFlow<LoginAndRegistrationStatus?>
        get() = _validation

    private suspend fun validationUser(email: String): LoginAndRegistrationStatus {
        val user = repository.getUser(email)
        return if(user.isEmpty()) LoginAndRegistrationStatus.AllGood else LoginAndRegistrationStatus.EmailAlreadyExists
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun createUser(email: String, pass: String, copyPass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if(
                (email != "" && email.isNotEmpty()) &&
                (pass != "" && pass.isNotEmpty()) &&
                (copyPass != "" && copyPass.isNotEmpty())
            ) {
                if(isEmailValid(email)) {
                    if(pass == copyPass) {
                        val userAlive = validationUser(email)
                        _validation.value = userAlive
                        if(userAlive == LoginAndRegistrationStatus.AllGood) {
                            repository.createUser(email, pass)
                        }
                    }
                    else {
                        _validation.value = LoginAndRegistrationStatus.PasswordsDoNotMatch
                    }
                }
                else {
                    _validation.value = LoginAndRegistrationStatus.NotCorrectEmail
                }
            }
            else {
                _validation.value = LoginAndRegistrationStatus.EmptyField
            }
        }
    }
}
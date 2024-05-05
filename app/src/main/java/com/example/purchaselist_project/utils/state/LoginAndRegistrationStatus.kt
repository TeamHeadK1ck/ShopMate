package com.example.purchaselist_project.utils.state

enum class LoginAndRegistrationStatus(val positiveFeature:Boolean) {
    ///All
    EmptyField(false), AllGood(true),
    ///Login state
    ErrorPassword(false), ErrorEmail(false),
    /// Registration State
    EmailAlreadyExists(false), NotCorrectEmail(false), PasswordsDoNotMatch(false)
}
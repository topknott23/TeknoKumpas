package com.example.teknokumpas.login

interface LoginContract {
    interface View {
        fun navigateToDashboard(userName: String)
        fun navigateToRegister()
        fun showError(message: String)
    }

    interface Presenter {

        fun handleLoginClicked(idNumber: String, pass: String)
        fun handleSignUpClicked()
    }
}
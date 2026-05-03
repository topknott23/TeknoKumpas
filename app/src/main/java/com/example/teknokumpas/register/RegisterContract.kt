package com.example.teknokumpas.register

interface RegisterContract {
    interface View {
        fun onRegistrationSuccess(fullName: String)
        fun navigateBackToLogin()
        fun showError(message: String)
    }

    interface Presenter {

        fun handleSignUpClicked(fullName: String, studentId: String, password: String)
        fun handleLoginLinkClicked()
    }
}
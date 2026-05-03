package com.example.teknokumpas.register

class RegisterPresenter(private val view: RegisterContract.View, private val model: RegisterModel) : RegisterContract.Presenter {
    override fun handleSignUpClicked(fullName: String, studentId: String, password: String) {
        if (fullName.isEmpty() || studentId.isEmpty() || password.isEmpty()) {
            view.showError("All fields are required")
            return
        }

        val success = model.register(fullName, studentId, password)
        if (success) {
            view.onRegistrationSuccess(fullName)
        } else {
            view.showError("ID Number is already registered")
        }
    }

    override fun handleLoginLinkClicked() {
        view.navigateBackToLogin()
    }
}
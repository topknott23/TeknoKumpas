package com.example.teknokumpas.login

class LoginPresenter(private val view: LoginContract.View, private val model: LoginModel) : LoginContract.Presenter {
    override fun handleLoginClicked(idNumber: String, pass: String) {
        if (idNumber.isEmpty() || pass.isEmpty()) {
            view.showError("Please enter your ID and Password")
            return
        }

        val success = model.login(idNumber, pass)
        if (success) {

            view.navigateToDashboard(model.getActiveUser())
        } else {
            view.showError("Incorrect ID or Password!")
        }
    }

    override fun handleSignUpClicked() {
        view.navigateToRegister()
    }
}
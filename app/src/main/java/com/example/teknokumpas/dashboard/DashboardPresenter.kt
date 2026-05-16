package com.example.teknokumpas.dashboard

class DashboardPresenter(private val view: DashboardContract.View) : DashboardContract.Presenter {
    override fun onProfileClicked(userName: String) {
        view.navigateToProfile(userName)
    }

    override fun onMapClicked() {
        view.navigateToDirectory()
    }

    override fun onLogoutClicked() {
        view.performLogout()
    }
}
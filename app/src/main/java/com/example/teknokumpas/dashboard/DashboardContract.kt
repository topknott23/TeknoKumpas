package com.example.teknokumpas.dashboard

interface DashboardContract {
    interface View {
        fun navigateToProfile(userName: String)
        fun showMapMessage(message: String)
        fun performLogout()
    }

    interface Presenter {
        fun onProfileClicked(userName: String)
        fun onMapClicked()
        fun onLogoutClicked()
    }
}
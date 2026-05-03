package com.example.teknokumpas.profile

interface ProfileContract {
    interface View {
        fun displayUserData(fullName: String, firstName: String, middleName: String, lastName: String, email: String)
        fun closeProfile()
    }

    interface Presenter {
        fun onViewCreated(fullName: String)
        fun onBackClicked()
    }
}
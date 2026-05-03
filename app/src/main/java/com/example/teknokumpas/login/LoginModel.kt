package com.example.teknokumpas.login

import com.example.teknokumpas.data.repositories.UserRepository

class LoginModel {
    fun login(idNumber: String, pass: String): Boolean {
        return UserRepository.login(idNumber, pass)
    }
    fun getActiveUser(): String = UserRepository.activeUserName
}
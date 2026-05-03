package com.example.teknokumpas.register

import com.example.teknokumpas.data.repositories.UserRepository

class RegisterModel {
    fun register(fullName: String, idNumber: String, pass: String): Boolean {
        return UserRepository.register(fullName, idNumber, pass)
    }
}
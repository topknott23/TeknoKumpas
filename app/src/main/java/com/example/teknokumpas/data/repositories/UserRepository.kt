package com.example.teknokumpas.data.repositories

import com.example.teknokumpas.data.models.User

object UserRepository {

    private val users = mutableMapOf<String, User>()


    var activeUserName: String = ""

    fun register(fullName: String, idNumber: String, pass: String): Boolean {
        if (users.containsKey(idNumber)) return false // User already exists


        users[idNumber] = User(fullName, idNumber, pass)
        return true
    }

    fun login(idNumber: String, pass: String): Boolean {
        val foundUser = users[idNumber]

        // Check if the user exists AND the password matches
        if (foundUser != null && foundUser.pass == pass) {
            activeUserName = foundUser.fullName
            return true
        }
        return false
    }
}
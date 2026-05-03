package com.example.teknokumpas.profile

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {
    override fun onViewCreated(fullName: String) {
        val parts = fullName.trim().split(" ")

        var firstName = ""
        var middleName = ""
        var lastName = ""

        if (parts.size == 1) {
            firstName = parts[0]
        } else if (parts.size == 2) {
            firstName = parts[0]
            lastName = parts[1]
        } else {
            lastName = parts.last()

            val possibleMiddle = parts[parts.size - 2]
            if (possibleMiddle.length <= 2 || possibleMiddle.endsWith(".")) {
                middleName = possibleMiddle
                firstName = parts.subList(0, parts.size - 2).joinToString(" ")
            } else {
                firstName = parts.subList(0, parts.size - 1).joinToString(" ")
            }
        }


        val emailFirst = firstName.replace(" ", "").lowercase()
        val emailLast = lastName.replace(" ", "").lowercase()
        val email = if (lastName.isNotEmpty()) "$emailFirst.$emailLast@cit.edu" else "$emailFirst@cit.edu"

        view.displayUserData(fullName, firstName, middleName, lastName, email)
    }

    override fun onBackClicked() {
        view.closeProfile()
    }
}
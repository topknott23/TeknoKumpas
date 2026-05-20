package com.example.teknokumpas.directory

interface NavigationContract {
    interface View {
        fun displayDestination(latitude: Double, longitude: Double, title: String)
    }

    interface Presenter {
        fun loadDestinationCoordinates(destinationName: String)
    }
}
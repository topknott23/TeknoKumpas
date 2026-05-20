package com.example.teknokumpas.directory

class NavigationPresenter(private val view: NavigationContract.View) : NavigationContract.Presenter {

    override fun loadDestinationCoordinates(destinationName: String) {
        val (lat, lng) = when (destinationName) {
            "NGE Building" -> Pair(10.2951, 123.8815)
            "GLE Building" -> Pair(10.2958, 123.8809)
            "SAL Building" -> Pair(10.2954, 123.8812)
            else -> Pair(10.2957, 123.8811)
        }

        view.displayDestination(lat, lng, destinationName)
    }
}
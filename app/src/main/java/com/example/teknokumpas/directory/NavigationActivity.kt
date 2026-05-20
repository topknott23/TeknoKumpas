package com.example.teknokumpas.directory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class NavigationActivity : AppCompatActivity(), OnMapReadyCallback, NavigationContract.View {

    private lateinit var mMap: GoogleMap
    private lateinit var presenter: NavigationContract.Presenter

    private var targetLat: Double = 0.0
    private var targetLng: Double = 0.0
    private var markerTitle: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        presenter = NavigationPresenter(this)

        val destinationName = intent.getStringExtra("DESTINATION_NAME") ?: "CIT-U"
        presenter.loadDestinationCoordinates(destinationName)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun displayDestination(latitude: Double, longitude: Double, title: String) {
        targetLat = latitude
        targetLng = longitude
        markerTitle = title
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val destination = LatLng(targetLat, targetLng)
        mMap.addMarker(MarkerOptions().position(destination).title(markerTitle))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 18.5f))
    }
}
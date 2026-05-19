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

class NavigationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var destinationName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        destinationName = intent.getStringExtra("DESTINATION_NAME") ?: "CIT-U"

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val destinationCoordinates = when (destinationName) {
            "NGE Building" -> LatLng(10.2951, 123.8815)
            "GLE Building" -> LatLng(10.2958, 123.8809)
            "SAL Building" -> LatLng(10.2954, 123.8812)
            else -> LatLng(10.2957, 123.8811)
        }

        mMap.addMarker(MarkerOptions().position(destinationCoordinates).title(destinationName))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationCoordinates, 18.5f))
    }
}
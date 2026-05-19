package com.example.teknokumpas.directory

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val destName = intent.getStringExtra("DESTINATION_NAME") ?: "Unknown Location"

        val tvRoute = findViewById<TextView>(R.id.tvActiveRoute)
        tvRoute.text = "Navigating to: $destName"

        val btnBack = findViewById<TextView>(R.id.btnBackNav)
        btnBack.setOnClickListener {
            finish()
        }
    }
}
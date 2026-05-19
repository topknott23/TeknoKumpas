package com.example.teknokumpas.profile

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private lateinit var presenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter = ProfilePresenter(this)

        val userName = intent.getStringExtra("USER_NAME") ?: "Guest"
        presenter.onViewCreated(userName)

        val btnBack = findViewById<Button>(R.id.btnBackToDashboard)
        btnBack.setOnClickListener {
            presenter.onBackClicked()
        }
        val btnBackBottom = findViewById<android.widget.Button>(R.id.btnBackToDashboard)

        btnBackBottom.setOnClickListener {
            finish()
        }
        val btnBackTop = findViewById<android.widget.TextView>(R.id.btnBackProfileTop)

        btnBackTop.setOnClickListener {
            finish()
        }
    }

    override fun displayUserData(fullName: String, firstName: String, middleName: String, lastName: String, email: String) {
        findViewById<TextView>(R.id.tvDisplayUsername).text = fullName
        findViewById<TextView>(R.id.tvFirstName).text = firstName
        findViewById<TextView>(R.id.tvMiddleName).text = if (middleName.isEmpty()) "N/A" else middleName
        findViewById<TextView>(R.id.tvLastName).text = lastName
        findViewById<TextView>(R.id.tvEmail).text = email
    }

    override fun closeProfile() {
        finish()
    }
}
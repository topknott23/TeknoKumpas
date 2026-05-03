package com.example.teknokumpas.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R
import com.example.teknokumpas.login.LoginActivity
import com.example.teknokumpas.profile.ProfileActivity

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private lateinit var presenter: DashboardPresenter
    private var currentUserName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter = DashboardPresenter(this)

        currentUserName = intent.getStringExtra("USER_NAME") ?: "Guest"

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val firstName = currentUserName.split(" ")[0]
        tvWelcome.text = "Hello, $firstName!"

        val cvProfile = findViewById<View>(R.id.cvProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val rlMap = findViewById<View>(R.id.rlMap)

        cvProfile.setOnClickListener { presenter.onProfileClicked(currentUserName) }
        btnLogout.setOnClickListener { presenter.onLogoutClicked() }
        rlMap.setOnClickListener { presenter.onMapClicked() }
    }

    override fun navigateToProfile(userName: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
    }

    override fun showMapMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun performLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
    }
}
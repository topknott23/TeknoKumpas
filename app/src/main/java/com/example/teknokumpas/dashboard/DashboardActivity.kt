package com.example.teknokumpas.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R
import com.example.teknokumpas.directory.DirectoryActivity
import com.example.teknokumpas.login.LoginActivity
import com.example.teknokumpas.profile.ProfileActivity

class DashboardActivity : AppCompatActivity(), DashboardContract.View {

    private lateinit var presenter: DashboardContract.Presenter
    private var tvDestination: TextView? = null // Made nullable so it won't crash if missing!

    private val directoryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val destination = result.data?.getStringExtra("DESTINATION_NAME")
            if (destination != null) {
                tvDestination?.text = "Navigating to: $destination"
                tvDestination?.visibility = View.VISIBLE
                Toast.makeText(this, "Route calculated!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        presenter = DashboardPresenter(this)

        val rawUserName = intent.getStringExtra("USER_NAME")
        val userName = if (rawUserName.isNullOrEmpty()) "Student" else rawUserName

        // SAFE CALLS: The app will no longer crash if these IDs don't perfectly match your XML
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome?.text = "Welcome, $userName!"

        tvDestination = findViewById(R.id.tvDestination)

        val cvProfile = findViewById<View>(R.id.cvProfile)
        cvProfile?.setOnClickListener {
            presenter.onProfileClicked(userName)
        }

        val rlMap = findViewById<View>(R.id.rlMap)
        rlMap?.setOnClickListener {
            presenter.onMapClicked()
        }

        val btnLogout = findViewById<View>(R.id.btnLogout)
        btnLogout?.setOnClickListener {
            presenter.onLogoutClicked()
        }

        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        })
    }

    override fun navigateToProfile(userName: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
    }

    override fun navigateToDirectory() {
        val intent = Intent(this, DirectoryActivity::class.java)
        directoryLauncher.launch(intent)
    }

    override fun performLogout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
package com.example.teknokumpas.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R
import com.example.teknokumpas.dashboard.DashboardActivity
import com.example.teknokumpas.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter = LoginPresenter(this, LoginModel())


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        val etIdNumber = findViewById<EditText>(R.id.etIdNumber)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        btnLogin.setOnClickListener {
            val id = etIdNumber.text.toString()
            val pass = etPassword.text.toString()


            presenter.handleLoginClicked(id, pass)
        }
        tvSignUp.setOnClickListener {
            presenter.handleSignUpClicked()
        }
    }

    override fun navigateToDashboard(userName: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("USER_NAME", userName)
        startActivity(intent)
    }

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}









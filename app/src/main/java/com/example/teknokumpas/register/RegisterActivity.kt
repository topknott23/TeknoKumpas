package com.example.teknokumpas.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R
import com.example.teknokumpas.login.LoginActivity

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter = RegisterPresenter(this, RegisterModel())

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)
        val etFullName = findViewById<EditText>(R.id.etFullName)

        btnSignUp.setOnClickListener {
            val name = etFullName.text.toString()
            val studentId = findViewById<EditText>(R.id.etStudentId).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()


            presenter.handleSignUpClicked(name, studentId, password)
        }

        tvLogin.setOnClickListener {
            presenter.handleLoginLinkClicked()
        }
    }

    override fun onRegistrationSuccess(fullName: String) {
        val studentId = findViewById<android.widget.EditText>(R.id.etStudentId).text.toString()
        val password = findViewById<android.widget.EditText>(R.id.etPassword).text.toString()

        android.widget.Toast.makeText(this, "Registration Successful!", android.widget.Toast.LENGTH_SHORT).show()
        val intent = android.content.Intent(this, com.example.teknokumpas.login.LoginActivity::class.java)


        intent.putExtra("REGISTERED_NAME", fullName)
        intent.putExtra("REGISTERED_ID", studentId)
        intent.putExtra("REGISTERED_PASS", password)

        startActivity(intent)
        finish()
    }

    override fun navigateBackToLogin() {
        finish()
    }
    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
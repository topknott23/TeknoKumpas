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
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etIdNumber = findViewById<EditText>(R.id.etIdNumber)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)

        btnLogin.setOnClickListener {
            val idNumber = etIdNumber.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (idNumber.isNotEmpty() && password.isNotEmpty()) {
                loginUser(idNumber, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(idNumber: String, password: String) {
        val json = JSONObject()
        json.put("email", idNumber)
        json.put("password", password)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2/teknokumpas_api/login.php")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@LoginActivity, "Connection failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                runOnUiThread {
                    if (responseData != null) {
                        try {
                            val jsonObject = JSONObject(responseData)
                            val status = jsonObject.getString("status")
                            val message = jsonObject.getString("message")

                            if (status == "success") {
                                // SAFE GRAB: If full_name is missing from the database, it safely defaults to "Student" instead of breaking the app!
                                val fullName = jsonObject.optString("full_name", "Student")

                                Toast.makeText(this@LoginActivity, "Welcome back!", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                intent.putExtra("USER_NAME", fullName)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {

                            Toast.makeText(this@LoginActivity, "Crash prevented! Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
    }
}
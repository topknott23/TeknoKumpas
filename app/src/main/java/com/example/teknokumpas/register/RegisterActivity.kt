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
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Using your exact XML layout IDs
        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etStudentId = findViewById<EditText>(R.id.etStudentId)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnSignUp.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (fullName.isNotEmpty() && studentId.isNotEmpty() && password.isNotEmpty()) {
                // Pass the data to our network function
                registerUser(fullName, studentId, password)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(fullName: String, studentId: String, password: String) {
        val json = JSONObject()
        json.put("full_name", fullName)
        // We send the Student ID into the database's "email" column
        json.put("email", studentId)
        json.put("password", password)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toString().toRequestBody(mediaType)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2/teknokumpas_api/register.php")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Connection failed", Toast.LENGTH_SHORT).show()
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
                                Toast.makeText(this@RegisterActivity, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                finish() // Closes the register screen
                            } else {
                                Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@RegisterActivity, "Error parsing response", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }
}
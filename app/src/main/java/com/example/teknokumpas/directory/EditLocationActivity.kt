package com.example.teknokumpas.directory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R

class EditLocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        val etName = findViewById<EditText>(R.id.etEditName)
        val etDesc = findViewById<EditText>(R.id.etEditDesc)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnBack = findViewById<TextView>(R.id.btnBackEdit)

        val position = intent.getIntExtra("position", -1)
        val currentName = intent.getStringExtra("name")
        val currentDesc = intent.getStringExtra("description")

        etName.setText(currentName)
        etDesc.setText(currentDesc)

        btnBack.setOnClickListener {
            finish() // Instantly closes the edit screen and goes back
        }

        btnUpdate.setOnClickListener {
            val updatedName = etName.text.toString().trim()
            val updatedDesc = etDesc.text.toString().trim()

            if (updatedName.isNotEmpty() && updatedDesc.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("action", "EDIT")
                resultIntent.putExtra("position", position)
                resultIntent.putExtra("name", updatedName)
                resultIntent.putExtra("description", updatedDesc)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("action", "DELETE")
            resultIntent.putExtra("position", position)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
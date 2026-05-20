package com.example.teknokumpas.directory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R

class EditLocationActivity : AppCompatActivity(), EditLocationContract.View {

    private lateinit var presenter: EditLocationContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        presenter = EditLocationPresenter(this)

        val etName = findViewById<EditText>(R.id.etEditName)
        val etDesc = findViewById<EditText>(R.id.etEditDesc)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnBack = findViewById<TextView>(R.id.btnBackEdit)

        val position = intent.getIntExtra("position", -1)
        val currentName = intent.getStringExtra("name") ?: ""
        val currentDesc = intent.getStringExtra("description") ?: ""

        etName.setText(currentName)
        etDesc.setText(currentDesc)

        btnBack.setOnClickListener { presenter.onBackClicked() }

        btnUpdate.setOnClickListener {
            presenter.onUpdateClicked(position, etName.text.toString().trim(), etDesc.text.toString().trim())
        }

        btnDelete.setOnClickListener { presenter.onDeleteClicked(position) }
    }

    override fun finishWithEditResult(position: Int, name: String, desc: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("action", "EDIT")
        resultIntent.putExtra("position", position)
        resultIntent.putExtra("name", name)
        resultIntent.putExtra("description", desc)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun finishWithDeleteResult(position: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("action", "DELETE")
        resultIntent.putExtra("position", position)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeScreen() {
        finish()
    }
}
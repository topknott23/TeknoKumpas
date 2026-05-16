package com.example.teknokumpas.directory

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.teknokumpas.R

data class CampusLocation(val name: String, val description: String)

class LocationAdapter(private val context: Context, private val dataSource: ArrayList<CampusLocation>) : BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: inflater.inflate(R.layout.item_location, parent, false)

        val nameTextView = rowView.findViewById<TextView>(R.id.tvLocName)
        val descTextView = rowView.findViewById<TextView>(R.id.tvLocDesc)

        val location = getItem(position) as CampusLocation
        nameTextView.text = location.name
        descTextView.text = location.description

        return rowView
    }
}

class DirectoryActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: LocationAdapter
    private val locationList = ArrayList<CampusLocation>()

    private val editLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val position = data?.getIntExtra("position", -1) ?: -1
            val action = data?.getStringExtra("action")

            if (position != -1) {
                if (action == "DELETE") {
                    locationList.removeAt(position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Location deleted", Toast.LENGTH_SHORT).show()
                } else if (action == "EDIT") {
                    val newName = data.getStringExtra("name") ?: ""
                    val newDesc = data.getStringExtra("description") ?: ""
                    locationList[position] = CampusLocation(newName, newDesc)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Location updated", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        listView = findViewById(R.id.lvDirectory)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        locationList.add(CampusLocation("NGE Building", "Engineering and Architecture"))
        locationList.add(CampusLocation("GLE Building", "Computer and IT Laboratories"))
        locationList.add(CampusLocation("SAL Building", "Science Laboratories"))

        adapter = LocationAdapter(this, locationList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selected = locationList[position]
            val intent = Intent(this, EditLocationActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("name", selected.name)
            intent.putExtra("description", selected.description)
            editLocationLauncher.launch(intent)
        }

        btnAdd.setOnClickListener {
            showAddLocationDialog()
        }
    }

    private fun showAddLocationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add Custom Location")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(64, 32, 64, 32)

        val nameInput = EditText(this)
        nameInput.hint = "Location Name"
        layout.addView(nameInput)

        val descInput = EditText(this)
        descInput.hint = "Description"
        layout.addView(descInput)

        builder.setView(layout)

        builder.setPositiveButton("Save") { dialog, _ ->
            val name = nameInput.text.toString().trim()
            val desc = descInput.text.toString().trim()

            if (name.isNotEmpty() && desc.isNotEmpty()) {
                locationList.add(CampusLocation(name, desc))
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "$name added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
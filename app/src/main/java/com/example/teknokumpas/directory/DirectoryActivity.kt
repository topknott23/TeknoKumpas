package com.example.teknokumpas.directory

import android.app.AlertDialog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
    private lateinit var tvEmptyState: TextView
    private lateinit var etSearch: EditText

    private val allLocations = ArrayList<CampusLocation>()
    private val displayedLocations = ArrayList<CampusLocation>()

    private val editLocationLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val position = data?.getIntExtra("position", -1) ?: -1
            val action = data?.getStringExtra("action")

            if (position != -1) {
                val targetLocation = displayedLocations[position]
                val originalName = targetLocation.name

                if (action == "DELETE") {
                    val json = JSONObject()
                    json.put("name", originalName)

                    val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                    val request = Request.Builder()
                        .url("http://10.0.2.2/teknokumpas_api/delete_location.php")
                        .post(requestBody)
                        .build()

                    OkHttpClient().newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            runOnUiThread { Toast.makeText(this@DirectoryActivity, "Failed to delete", Toast.LENGTH_SHORT).show() }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            runOnUiThread {
                                Toast.makeText(this@DirectoryActivity, "Location deleted", Toast.LENGTH_SHORT).show()
                                fetchLocationsFromServer()
                            }
                        }
                    })

                } else if (action == "EDIT") {
                    val newName = data.getStringExtra("name") ?: ""
                    val newDesc = data.getStringExtra("description") ?: ""

                    val json = JSONObject()
                    json.put("old_name", originalName)
                    json.put("new_name", newName)
                    json.put("description", newDesc)

                    val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                    val request = Request.Builder()
                        .url("http://10.0.2.2/teknokumpas_api/edit_location.php")
                        .post(requestBody)
                        .build()

                    OkHttpClient().newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            runOnUiThread { Toast.makeText(this@DirectoryActivity, "Failed to update", Toast.LENGTH_SHORT).show() }
                        }
                        override fun onResponse(call: Call, response: Response) {
                            runOnUiThread {
                                Toast.makeText(this@DirectoryActivity, "Location updated", Toast.LENGTH_SHORT).show()
                                fetchLocationsFromServer()
                            }
                        }
                    })
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        listView = findViewById(R.id.lvDirectory)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        etSearch = findViewById(R.id.etSearch)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnBack = findViewById<TextView>(R.id.btnBackDirectory)

//        allLocations.add(CampusLocation("NGE Building", "Engineering and Architecture"))
//        allLocations.add(CampusLocation("GLE Building", "Computer and IT Laboratories"))
//        allLocations.add(CampusLocation("SAL Building", "Science Laboratories"))
//        allLocations.add(CampusLocation("Main Library", "Study Area"))
        fetchLocationsFromServer()
        displayedLocations.addAll(allLocations)

        adapter = LocationAdapter(this, displayedLocations)
        listView.adapter = adapter
        updateEmptyState()

        btnBack.setOnClickListener {
            finish()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selected = displayedLocations[position]
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("DESTINATION_NAME", selected.name)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val selected = displayedLocations[position]
            val intent = Intent(this, EditLocationActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("name", selected.name)
            intent.putExtra("description", selected.description)
            editLocationLauncher.launch(intent)
            true
        }

        btnAdd.setOnClickListener {
            showAddLocationDialog()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterLocations(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterLocations(query: String) {
        displayedLocations.clear()
        if (query.isEmpty()) {
            displayedLocations.addAll(allLocations)
        } else {
            val lowerCaseQuery = query.lowercase()
            for (location in allLocations) {
                if (location.name.lowercase().contains(lowerCaseQuery) ||
                    location.description.lowercase().contains(lowerCaseQuery)) {
                    displayedLocations.add(location)
                }
            }
        }
        adapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun updateEmptyState() {
        if (displayedLocations.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            listView.visibility = View.VISIBLE
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
                // 1. Create the JSON data to send
                val json = JSONObject()
                json.put("name", name)
                json.put("description", desc)

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = json.toString().toRequestBody(mediaType)

                // 2. Build the POST request
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.0.2.2/teknokumpas_api/add_location.php")
                    .post(requestBody)
                    .build()


                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(this@DirectoryActivity, "Failed to save to server", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        runOnUiThread {

                            Toast.makeText(this@DirectoryActivity, "$name saved to database!", Toast.LENGTH_SHORT).show()
                            fetchLocationsFromServer()
                        }
                    }
                })

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
    private fun fetchLocationsFromServer() {
        val client = OkHttpClient()
        // Use 10.0.2.2 to connect the emulator to your PC's XAMPP server
        val request = Request.Builder()
            .url("http://10.0.2.2/teknokumpas_api/get_locations.php")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@DirectoryActivity, "Failed to connect to database", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (responseData != null) {
                    try {
                        val jsonObject = JSONObject(responseData)
                        if (jsonObject.getString("status") == "success") {
                            val jsonArray = jsonObject.getJSONArray("data")

                            // Clear old hardcoded data
                            allLocations.clear()

                            // Loop through the MySQL data and add it to our Kotlin list
                            for (i in 0 until jsonArray.length()) {
                                val item = jsonArray.getJSONObject(i)
                                val name = item.getString("name")
                                val desc = item.getString("description")
                                allLocations.add(CampusLocation(name, desc))
                            }

                            // Update the screen!
                            runOnUiThread {
                                filterLocations(etSearch.text.toString())
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}
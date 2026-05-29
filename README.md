# 🗺️ Tekno-Kumpas (A project in our MobDev Subject)

**Tekno-Kumpas** is a mobile Android application built to help freshmen navigate the Cebu Institute of Technology – University (CIT-U) campus. It provides a dynamic directory of campus facilities and integrates real-time mapping to ensure students can track down their classes, laboratories, and study areas without getting lost. 

##  Features

* **User Authentication:** Secure Login and Registration system for students.
* **Dynamic Campus Directory:** Fetches real-time campus locations (e.g., NGE, GLE, and SAL Buildings) from a custom backend database.
* **Interactive Navigation:** Integrates the **Google Maps SDK** to plot exact latitude and longitude coordinates for selected campus buildings.
* **Location Management (Admin):** Long-press functionality allowing users to edit or delete custom campus locations dynamically, reflecting instantly on the server.
* **Student Dashboard & Profile:** A centralized hub to manage user details and access core navigation features.

##  Architecture & Tech Stack

This project strictly adheres to the **Model-View-Presenter (MVP)** architectural pattern, implemented using a **Vertical Slicing** approach. Rather than building horizontal layers, each core feature (Authentication, Dashboard, Profile, etc.) is developed as a complete, independent, and testable slice from the UI down to the data logic.

* **Language:** Kotlin
* **UI toolkit:** Android XML / Material Design
* **Architecture:** MVP (Model-View-Presenter)
* **Networking:** OkHttp (for REST API communication)
* **Backend:** PHP / MySQL (Local server via XAMPP)
* **External APIs:** Google Maps SDK

##  Setup and Installation

Since the app connects to a local database for the directory, follow these steps to run the project in your local environment:

### Prerequisites
* Android Studio (Ladybug or newer recommended)
* XAMPP (or any local Apache/MySQL server)
* A valid Google Maps API Key

### 1. Database Setup
1. Start Apache and MySQL in your XAMPP control panel.
2. Create a database named `teknokumpas_db`.
3. Ensure your PHP API files (`get_locations.php`, `add_location.php`, etc.) are located in your `htdocs/teknokumpas_api` folder.

### 2. Android Studio Setup
1. Clone this repository:
   ```bash
   git clone [https://github.com/yourusername/teknokumpas.git](https://github.com/yourusername/teknokumpas.git)
2. Open the project in Android Studio.
3. Open AndroidManifest.xml and insert your Google Maps API key:
   <meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY_HERE" />
4. Build and sync the Gradle files.
5. Run the application on an emulator. (Note: The app is configured to point to http://10.0.2.2/ to allow the Android Emulator to communicate with your localhost XAMPP server).

##  Future Improvements
* Refactor the DirectoryActivity API calls into a dedicated Presenter to completely unify the MVP architecture across all network requests.
* Add indoor floorplan tracking for multi-story buildings.
* Implement a search filter for finding specific faculty offices.
* Make the add location actually pinpoint the location you wanted

  Developed as a project to make campus life a little easier for the next batch of CIT-U students.

# TeknoKumpas

Welcome to the repository for TeknoKumpas. I originally put this together for my CSIT 284 Platform Development course, but I think you might find it genuinely useful if you have ever struggled to navigate around the CIT-U campus. 

The idea is simple: finding your way to specific buildings should not be a hassle. I highly recommend giving this app a spin if you want a cleaner, more tailored campus navigation experience.

## Why use TeknoKumpas?

If you decide to try it out, here are a few things you will be able to do:
* **Campus Navigation:** I integrated the Google Maps SDK so the app can guide you directly to key buildings like NGE, GLE, and SAL.
* **Account Management:** You can create your own account, log in securely, and set up a personalized profile. 
* **Location Directory:** If you are ever unsure where something is, you can browse the built-in directory to check specific campus locations.

## Under the Hood

If you are a developer looking to explore the code, you will find that the architecture is quite straightforward. I would suggest looking into how the different activities communicate. Here is the core stack I used to build this:
* **Language:** Kotlin
* **Platform:** Android SDK (Target API 31)
* **Mapping:** Google Maps API

## How to Run it Locally

If you want to build and run this project on your own machine, I recommend following these steps to get it working smoothly:

1. **Clone the repository** to your local machine using Git.
2. **Set up the Maps API Key.** For security reasons, I do not keep my Google Maps API key in the public code. You will need to generate your own free key from the Google Cloud Console.
3. Once you have your key, create a file named `local.properties` in the root directory of the Android project and add this exact line:
   `MAPS_API_KEY=your_actual_api_key_here`
4. **Sync and Run.** Open the project in Android Studio, allow Gradle to sync the dependencies, and you should be good to run it on your emulator or physical device.

## Final Thoughts

Feel free to poke around the code, fork the project, or use it as a reference for your own Android applications. If you have any suggestions on how to make the routing better or want to add more buildings to the directory, I would definitely encourage you to open a pull request or drop an issue.

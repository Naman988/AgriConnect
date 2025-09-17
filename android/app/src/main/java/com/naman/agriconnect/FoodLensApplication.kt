package com.naman.agriconnect

import android.app.Application
import com.google.firebase.FirebaseApp

class FoodLensApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase here, right at the start of the app lifecycle.
        FirebaseApp.initializeApp(this)
    }
}


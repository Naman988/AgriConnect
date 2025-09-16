package com.naman.agriconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.naman.agriconnect.ui.theme.AgriConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This line installs and runs our splash screen
        installSplashScreen()
        setContent {
            // This applies our custom green theme to the entire app
            AgriConnectTheme {
                // This displays our login UI and navigation
                AppNavigation()
            }
        }
    }
}

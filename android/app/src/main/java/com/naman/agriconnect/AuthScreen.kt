package com.naman.agriconnect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- UI Screens (Composables) ---

@Composable
fun PhoneLoginScreen(
    onSendOtpClicked: (String) -> Unit,
) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter Phone Number", fontSize = 24.sp, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("10-digit mobile number") },
            leadingIcon = { Text("+91") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onSendOtpClicked("+91" + phoneNumber) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send OTP")
        }
    }
}

@Composable
fun OtpScreen(
    onVerifyOtpClicked: (String) -> Unit,
) {
    var otp by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter OTP", fontSize = 24.sp, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("6-digit OTP") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onVerifyOtpClicked(otp) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify OTP")
        }
    }
}

@Composable
fun MainAppScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to AgriConnect!", fontSize = 28.sp)
    }
}

// --- Navigation Setup ---

sealed class Screen(val route: String) {
    object PhoneLogin : Screen("phone_login")
    object Otp : Screen("otp")
    object MainApp : Screen("main_app")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // We will create the AuthViewModel in a future step to handle the logic.

    NavHost(navController = navController, startDestination = Screen.PhoneLogin.route) {
        composable(Screen.PhoneLogin.route) {
            PhoneLoginScreen(
                onSendOtpClicked = { phoneNumber ->
                    // For now, just navigate to the OTP screen for UI testing.
                    navController.navigate(Screen.Otp.route)
                }
            )
        }
        composable(Screen.Otp.route) {
            OtpScreen(
                onVerifyOtpClicked = { otp ->
                    // For now, just navigate to the main app screen for UI testing.
                    navController.navigate(Screen.MainApp.route)
                }
            )
        }
        composable(Screen.MainApp.route) {
            MainAppScreen()
        }
    }
}

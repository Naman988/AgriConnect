package com.naman.agriconnect

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- Reusable Gradient Background ---
@Composable
fun GradientBackground(content: @Composable BoxScope.() -> Unit) {
    // A softer, more formal green gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF1F8E9), Color(0xFFDCEDC8), Color(0xFFC5E1A5))
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        content()
    }
}


// --- UI Screens (Composables) ---
@Composable
fun PhoneLoginScreen(
    authState: AuthState,
    onSendOtpClicked: (String) -> Unit,
    onNavigateToAdminLogin: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            Toast.makeText(context, authState.message, Toast.LENGTH_LONG).show()
        }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(color = Color(0xFF33691E))
            } else {
                Spacer(modifier = Modifier.weight(1f)) // Pushes content to center

                // Reverted to circular card logo style
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_loginscreenlogo_full),
                        contentDescription = "FoodLens Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    "Sign In",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("10-digit mobile number") },
                    leadingIcon = { Text("+91") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color(0xFF33691E),
                        cursorColor = Color(0xFF33691E)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onSendOtpClicked("+91" + phoneNumber) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF558B2F))
                ) {
                    Text("Send OTP", fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
                // Officials Login link
                Text(
                    text = "Officials Login",
                    modifier = Modifier.clickable { onNavigateToAdminLogin() },
                    color = Color(0xFF0D47A1), // Professional dark blue
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline
                )

                Spacer(modifier = Modifier.weight(1f)) // Pushes tagline to bottom

                // Highlighted tagline
                Text(
                    "Food Transparency Made Simple",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF33691E), // Darker theme green
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun OtpScreen(
    authState: AuthState,
    onVerifyOtpClicked: (String) -> Unit
) {
    var otp by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(authState) {
        if (authState is AuthState.Error) {
            Toast.makeText(context, authState.message, Toast.LENGTH_LONG).show()
        }
    }

    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (authState is AuthState.Loading) {
                CircularProgressIndicator(color = Color(0xFF33691E))
            } else {
                Spacer(modifier = Modifier.weight(1f))

                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo_full),
                        contentDescription = "FoodLens Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "Verify OTP",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )
                Text(
                    "Enter the 6-digit code sent to your phone",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("6-digit OTP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color(0xFF33691E),
                        cursorColor = Color(0xFF33691E)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onVerifyOtpClicked(otp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF558B2F))
                ) {
                    Text("Verify & Continue", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    "Food Transparency Made Simple",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF33691E),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AdminLoginScreen(
    onAdminLoginClicked: (String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            // Back button
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.DarkGray
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Enhanced warning section
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF33691E).copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info, // Changed to Info icon
                        contentDescription = "Information",
                        tint = Color(0xFF33691E) // Dark theme green
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "For Official Use Only",
                        color = Color(0xFF33691E),
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))

                Text("Admin Login", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color(0xFF33691E),
                        cursorColor = Color(0xFF33691E)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.5f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                        focusedIndicatorColor = Color(0xFF33691E),
                        cursorColor = Color(0xFF33691E)
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onAdminLoginClicked(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF558B2F))
                ) {
                    Text("Login", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    GradientBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Welcome to FoodLens!", fontSize = 28.sp, color = Color(0xFF333333))
        }
    }
}


// --- Navigation Setup ---
sealed class Screen(val route: String) {
    object PhoneLogin : Screen("phone_login")
    object Otp : Screen("otp")
    object AdminLogin : Screen("admin_login")
    object MainApp : Screen("main_app")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.OtpSent -> {
                navController.navigate(Screen.Otp.route)
            }
            is AuthState.LoggedIn -> {
                navController.navigate(Screen.MainApp.route) {
                    popUpTo(Screen.PhoneLogin.route) { inclusive = true }
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.PhoneLogin.route) {
        composable(Screen.PhoneLogin.route) {
            PhoneLoginScreen(
                authState = authState,
                onSendOtpClicked = { phoneNumber ->
                    authViewModel.sendOtp(phoneNumber, context as Activity)
                },
                onNavigateToAdminLogin = {
                    navController.navigate(Screen.AdminLogin.route)
                }
            )
        }
        composable(Screen.Otp.route) {
            OtpScreen(
                authState = authState,
                onVerifyOtpClicked = { otp ->
                    authViewModel.verifyOtp(otp)
                }
            )
        }
        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(
                onAdminLoginClicked = { email, password ->
                    // We will add the admin login logic to the ViewModel next
                    Toast.makeText(context, "Admin login coming soon!", Toast.LENGTH_SHORT).show()
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.MainApp.route) {
            MainAppScreen()
        }
    }
}


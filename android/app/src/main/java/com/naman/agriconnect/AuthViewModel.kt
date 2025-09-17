package com.naman.agriconnect

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

// Defines the different states our authentication flow can be in
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object OtpSent : AuthState()
    object LoggedIn : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    // Lazily try to get FirebaseAuth; if it fails we store null and continue (no crash)
    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.e("AuthViewModel", "FirebaseAuth.getInstance() failed", e)
            null
        }
    }

    private var verificationId: String? = null
    val authState = mutableStateOf<AuthState>(AuthState.Idle)

    init {
        // Early check: if auth is null, set an error state so UI can react.
        if (auth == null) {
            val msg = "FirebaseAuth not available. Did you initialize Firebase and add google-services.json?"
            Log.e("AuthViewModel", msg)
            // We set Error here but keep the VM alive so UI can display proper message.
            authState.value = AuthState.Error(msg)
        }
    }

    /**
     * Send OTP to the phone number using Firebase Phone Auth.
     * Note: activity must be passed because PhoneAuthOptions requires it.
     */
    fun sendOtp(phoneNumber: String, activity: Activity) {
        // Guard: if auth is not available, bail out with a readable error
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            val msg = "Cannot send OTP: FirebaseAuth is not initialized."
            Log.e("AuthViewModel", msg)
            authState.value = AuthState.Error(msg)
            return
        }

        authState.value = AuthState.Loading

        try {
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.d("AuthViewModel", "onVerificationCompleted")
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Log.e("AuthViewModel", "Verification failed", e)
                        authState.value = AuthState.Error(e.message ?: "Verification failed")
                    }

                    override fun onCodeSent(
                        newVerificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken,
                    ) {
                        Log.d("AuthViewModel", "OTP Sent (onCodeSent)")
                        verificationId = newVerificationId
                        authState.value = AuthState.OtpSent
                    }
                })
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        } catch (e: Exception) {
            Log.e("AuthViewModel", "sendOtp: exception while building/verifying", e)
            authState.value = AuthState.Error(e.message ?: "Failed to send OTP")
        }
    }

    /**
     * Verify OTP entered by user.
     */
    fun verifyOtp(otp: String) {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            val msg = "Cannot verify OTP: FirebaseAuth is not initialized."
            Log.e("AuthViewModel", msg)
            authState.value = AuthState.Error(msg)
            return
        }

        if (verificationId == null) {
            authState.value = AuthState.Error("Verification process not started.")
            return
        }

        authState.value = AuthState.Loading
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    /**
     * Sign in using the phone auth credential.
     * Note: we handle firebase == null inside the method for safety.
     */
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val firebaseAuth = auth
        if (firebaseAuth == null) {
            val msg = "Cannot sign in: FirebaseAuth is not initialized."
            Log.e("AuthViewModel", msg)
            authState.value = AuthState.Error(msg)
            return
        }

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Sign in successful")
                    authState.value = AuthState.LoggedIn
                } else {
                    Log.e("AuthViewModel", "Sign in failed", task.exception)
                    val message = task.exception?.message ?: "Sign in failed"
                    authState.value = AuthState.Error(message)
                }
            }
    }
}

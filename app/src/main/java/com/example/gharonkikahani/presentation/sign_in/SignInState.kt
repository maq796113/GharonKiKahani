package com.example.gharonkikahani.presentation.sign_in

import androidx.compose.foundation.text.input.TextFieldState
import com.example.gharonkikahani.data.AuthResult

data class SignInState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val canLogin: Boolean = true,
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val authResult: AuthResult? = null
)

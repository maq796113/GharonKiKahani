package com.example.gharonkikahani.presentation.sign_up

import androidx.compose.foundation.text.input.TextFieldState
import com.example.gharonkikahani.data.AuthResult

data class SignUpState(
        val name: TextFieldState = TextFieldState(),
        val email: TextFieldState = TextFieldState(),
        val password: TextFieldState = TextFieldState(),
        val confirmPassword: TextFieldState = TextFieldState(),
        val profilePicture: String? = null,
        val isPasswordVisible: Boolean = false,
        val isSignUpSuccessful: Boolean = false,
        val signUpError: String? = null,
        val canSignUp: Boolean = true,
        val isSigningUp: Boolean = false,
        val isSignedUp: Boolean = false,
        val authResult: AuthResult? = null
    )

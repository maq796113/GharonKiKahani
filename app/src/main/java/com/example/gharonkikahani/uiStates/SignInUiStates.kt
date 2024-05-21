package com.example.gharonkikahani.uiStates

import com.example.gharonkikahani.data.AuthResult

data class SignInUiStates(
    val isSignInSuccessful: Boolean = false,
    val isSigningIn: Boolean = false,
    val signInErrorMessage: String? = null,
    val authResult: AuthResult? = null
)

package com.example.bodyanalysistool.uistates

import com.example.gharonkikahani.data.AuthResult

data class SignUpUIStates(
    val isSignUpSuccessful: Boolean = false,
    val isSigningUp: Boolean = false,
    val signUpErrorMessage: String? = null,
    val authResult: AuthResult? = null
)
package com.example.gharonkikahani.firebase.auth

import android.content.Intent
import android.content.IntentSender
import com.example.gharonkikahani.data.AuthResult
import com.example.gharonkikahani.data.User

interface AuthRepository {
    val currentUser: User?
    suspend fun loginUsingEmailPassword(email: String, password: String): AuthResult
    suspend fun loginUsingGoogleWithIntent(intent: Intent): AuthResult
    suspend fun loginGoogleIntentSender(): IntentSender?
    suspend fun signup(name: String, email: String, password: String): AuthResult
    suspend fun logout()
    fun deleteUser()
}
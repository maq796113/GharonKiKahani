package com.example.gharonkikahani.viewmodel

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharonkikahani.data.AuthResult
import com.example.gharonkikahani.data.User
import com.example.gharonkikahani.firebase.auth.AuthRepository
import com.example.gharonkikahani.presentation.sign_in.SignInState
import com.example.gharonkikahani.presentation.sign_up.SignUpState
import com.example.gharonkikahani.session.Session
import com.example.gharonkikahani.session.SessionCache
import com.example.gharonkikahani.uiStates.EmailStates
import com.example.gharonkikahani.uiStates.NameStates
import com.example.gharonkikahani.uiStates.PasswordStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionCache: SessionCache
//    private val firestoreRepository: FirestoreRepository
): ViewModel() {



    var signInState by mutableStateOf(SignInState())
        private set

    var signUpState by mutableStateOf(SignUpState())
        private set

    private val nameChannel = Channel<NameStates> {  }

    private val _nameState = MutableStateFlow(NameStates())
    val nameState = _nameState.asStateFlow()

    private val _emailState = MutableStateFlow(EmailStates())
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(PasswordStates())
    val passwordState = _passwordState.asStateFlow()



    fun saveUserSession() {
        sessionCache.saveSession(
            session = Session(
                geminiAIUiState = null,
                user = signUpState.authResult?.user
            )
        )
    }

    fun getSignedInUser(): User? = authRepository.currentUser()

    fun onChangedName(name: String) {
        _nameState.update {
            it.copy(
                name = name
            )
        }
    }

    fun onChangedEmail(email: String) {
        _emailState.update {
            it.copy(
                email = email
            )
        }
    }

    fun onChangedPassword(password: String) {
        _passwordState.update {
            it.copy(
                password = password
            )
        }
    }

    fun onProfilePicture(uri: String) {
        signUpState = signUpState.copy(
            profilePicture = uri
        )
    }

    fun toggleVisibilitySignUp() {
        signUpState = signUpState.copy(
            isPasswordVisible = !signUpState.isPasswordVisible
        )
    }

    fun toggleVisibilitySignIn() {
        signInState = signInState.copy(
            isPasswordVisible = !signInState.isPasswordVisible
        )
    }

    suspend fun loginUsingGoogleWithIntent(intent: Intent) = authRepository.loginUsingGoogleWithIntent(intent = intent)



    fun signup(name: String, email: String, password: String) {

        signUpState = signUpState.copy(
            isSigningUp = true
        )



        viewModelScope.launch {
            signUpState = signUpState.copy(
                authResult=authRepository.signup(name, email, password)
            )
        }

    }

    suspend fun loginGoogleIntentSender(): IntentSender? {
        signInState = signInState.copy(
            isLoggingIn = true
        )
        return authRepository.loginGoogleIntentSender()
    }

    fun loginUsingEmailPassword(email: String, password: String){
        signInState = signInState.copy(
            isLoggingIn = true
        )

        viewModelScope.launch {
            signInState = signInState.copy(
                authResult = authRepository.loginUsingEmailPassword(email, password)
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    fun deleteUser() {
        authRepository.deleteUser()
    }

    fun onSignInResult(result: AuthResult) {

        signInState = signInState.copy(
            isSignInSuccessful = result.user != null,
            signInError = result.errorMessage
        )
        if(result.user != null){
            signInState = signInState.copy(
                isLoggedIn = true
            )
            Log.d(TAG, "user is not null")

        }
        if(result.errorMessage != null){
            signInState = signInState.copy(
                isLoggingIn = false
            )
        }
    }

    fun onSignUpResult(result: AuthResult) {
        signUpState = signUpState.copy(
            isSignUpSuccessful = result.user != null,
            signUpError = result.errorMessage
        )
        Log.d("InsideViewModel", " signUpState.isSignUpSuccessful = ${signUpState.isSignUpSuccessful}")
        if(result.user != null){
            Log.d(TAG, "user is not null")
//            firestoreRepository.addNewUser(
//                user = result.user
//            )
        }
//        if(result.errorMessage != null){
//            _signUpState.value = signUpState.value.copy(
//                isSigningUp = false
//            )
//        }
    }


    fun resetSignInState() {
        signInState
    }

    fun resetSignUpState() {
        signUpState = SignUpState()
    }

    companion object{
        private const val TAG = "Authentication ViewModel"
    }







}
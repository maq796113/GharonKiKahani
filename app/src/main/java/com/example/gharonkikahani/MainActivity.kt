package com.example.gharonkikahani

import SignInAction
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gharonkikahani.presentation.get_started.GetStartedScreen
import com.example.gharonkikahani.presentation.profile.ProfileScreen
import com.example.gharonkikahani.presentation.sign_in.SignInScreen
import com.example.gharonkikahani.ui.theme.GharonKiKahaniTheme
import com.example.gharonkikahani.viewmodel.AuthViewModel
import com.example.gharonkikahani.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel by viewModels<SplashScreenViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition{
            !viewModel.isReady.value
        }
        setContent {
            GharonKiKahaniTheme {
                val authViewModel = hiltViewModel<AuthViewModel>()
                val state = authViewModel.signInState
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = authViewModel.loginUsingGoogleWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                authViewModel.onSignInResult(
                                    result = signInResult
                                )
                            }
                        }
                    }
                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = GetStartedScreen
                    ) {
                        composable<GetStartedScreen> {
                            GetStartedScreen(
                                onGettingStarted = {
                                    navController.navigate(SignInScreen)
                                }
                            )

                        }



                        composable<SignInScreen>{

                            LaunchedEffect(key1 = Unit) {
                                if(authViewModel.getSignedInUser() != null){
                                    navController.navigate(DashboardScreen)
                                }
                            }



                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful){
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in Successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(DashboardScreen)
                                    authViewModel.resetSignInState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onAction = {
                                    it.run {
                                        when(this) {
                                            is SignInAction.OnGoogleLoginClick -> {
                                                lifecycleScope.launch {
                                                    val signInIntentSender = authViewModel.loginGoogleIntentSender()
                                                    launcher.launch(
                                                        IntentSenderRequest.Builder(
                                                            signInIntentSender ?: return@launch
                                                            ).build()
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }

                                },

                            )
                        }
                        composable<DashboardScreen>{
                            authViewModel.getSignedInUser()?.let { user ->
                                ProfileScreen(
                                    userData = user,
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            authViewModel.logout()
                                            Toast.makeText(
                                                applicationContext,
                                                "Signed Out",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            navController.popBackStack()
                                        }
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object SignInScreen

@Serializable
object DashboardScreen

@Serializable
object GetStartedScreen

@Serializable
object RecipeScreen


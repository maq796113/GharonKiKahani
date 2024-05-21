package com.example.gharonkikahani

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gharonkikahani.presentation.get_started.GetStartedScreen
import com.example.gharonkikahani.presentation.profile.ProfileScreen
import com.example.gharonkikahani.presentation.sign_in.GoogleAuthUiClient
import com.example.gharonkikahani.presentation.sign_in.SignInScreen
import com.example.gharonkikahani.ui.theme.GharonKiKahaniTheme
import com.example.gharonkikahani.viewmodel.SignInViewModel
import com.example.gharonkikahani.viewmodel.SplashScreenViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    private val viewModel by viewModels<SplashScreenViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().setKeepOnScreenCondition{
            !viewModel.isReady.value
        }
        setContent {
            GharonKiKahaniTheme {
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
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignInUser() != null){
                                    navController.navigate(DashboardScreen)
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = {result ->
                                    if(result.resultCode == RESULT_OK){
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful){
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in Successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(DashboardScreen)
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },

                            )
                        }
                        composable<DashboardScreen>{
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
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

@Serializable
object SignInScreen

@Serializable
object DashboardScreen

@Serializable
object GetStartedScreen

@Serializable
object RecipeScreen


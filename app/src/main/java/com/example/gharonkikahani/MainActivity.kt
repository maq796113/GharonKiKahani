package com.example.gharonkikahani

import SignInAction
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gharonkikahani.presentation.camera.CameraScreen
import com.example.gharonkikahani.presentation.get_started.GetStartedScreen
import com.example.gharonkikahani.presentation.menu.MenuScreen
import com.example.gharonkikahani.presentation.profile.ProfileScreen
import com.example.gharonkikahani.presentation.recipe.RecipeScreen
import com.example.gharonkikahani.presentation.sign_in.SignInScreen
import com.example.gharonkikahani.presentation.sign_up.SignUpAction
import com.example.gharonkikahani.presentation.sign_up.SignUpScreen
import com.example.gharonkikahani.ui.theme.GharonKiKahaniTheme
import com.example.gharonkikahani.viewmodel.AuthViewModel
import com.example.gharonkikahani.viewmodel.GeminiAIViewModel1
import com.example.gharonkikahani.viewmodel.GeminiAIViewModel2
import com.example.gharonkikahani.viewmodel.GetSessionViewModel
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
            val authViewModel = hiltViewModel<AuthViewModel>()
            val geminiAIViewModel = hiltViewModel<GeminiAIViewModel1>()
            val signInState = authViewModel.signInState
            val signUpState = authViewModel.signUpState
            var selectedProfileImageUri by remember {
                mutableStateOf<Uri?>(null)
            }
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {uri ->
                    selectedProfileImageUri = uri
                }
            )

            GharonKiKahaniTheme {


                if (!hasRequiredPermissions()) {
                    ActivityCompat.requestPermissions(
                        this, CAMERAX_PERMISSIONS, 0
                    )
                }

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
                        composable<SignUpScreen> {

                            LaunchedEffect(key1 = selectedProfileImageUri) {
                                if (selectedProfileImageUri != null) {
                                    authViewModel.onProfilePicture(selectedProfileImageUri.toString())
                                }
                            }



                            SignUpScreen(
                                signUpState = signUpState,
                                onAction = {
                                    when(it) {
                                        is SignUpAction.OnLoginClick -> {
                                            navController.navigate(SignInScreen)
                                        }
                                        is SignUpAction.OnRegisterClick -> {
                                            authViewModel.signup(
                                                name=signUpState.name.text.toString(),
                                                email=signUpState.email.text.toString(),
                                                password=signUpState.password.text.toString()
                                            )


                                            if (signUpState.isSignUpSuccessful) {
                                                authViewModel.saveUserSession()
                                                navController.navigate(SignInScreen)

                                            }
                                            navController.navigate(SignInScreen)
                                        }
                                        is SignUpAction.OnProfilePickerClick -> {
                                            singlePhotoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                            authViewModel.onProfilePicture(selectedProfileImageUri.toString())
                                        }
                                        is SignUpAction.OnTogglePasswordVisibility -> {
                                            authViewModel.toggleVisibilitySignUp()
                                        }

                                    }

                                }
                            )

                        }



                        composable<SignInScreen>{

                            LaunchedEffect(key1 = signInState.isLoggedIn) {
                                val signedInUser = authViewModel.getSignedInUser()
                                if(signInState.isLoggedIn){
                                    println("I'm here $signedInUser")
                                    navController.navigate(DashboardScreen)
                                }
                            }



                            LaunchedEffect(key1 = signInState.isSignInSuccessful) {
                                if(signInState.isSignInSuccessful){
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in Successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate(DashboardScreen)
                                    authViewModel.resetSignInState()
                                }
                            }
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



                            SignInScreen(
                                state = signInState,
                                onAction = {
                                    when(it) {
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
                                        is SignInAction.OnSimpleLoginClick -> {
                                            authViewModel.loginUsingEmailPassword(
                                                email = signInState.email.text.toString(),
                                                password = signInState.password.text.toString()
                                            )
                                            signInState.authResult?.let { authResult ->
                                                authViewModel.onSignInResult(
                                                    result = authResult
                                                )
                                            }

                                            if (signInState.isSignInSuccessful) {
                                                navController.navigate(DashboardScreen)
                                            } else {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Sign in Failed",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                println(signInState.signInError)
                                            }

                                        }
                                        is SignInAction.OnTogglePasswordVisibility -> {
                                            authViewModel.toggleVisibilitySignIn()
                                        }

                                        is SignInAction.OnSignUpClick -> {
                                            navController.navigate(SignUpScreen)
                                        }

                                        else -> {
                                            println("ERROR: NO SUCH ACTION EXISTS")
                                        }
                                    }


                                },
                                onFieldInput = {}
                            )
                        }
                        composable<DashboardScreen>{
                            val getSessionViewModel = hiltViewModel<GetSessionViewModel>()
                            authViewModel.getSignedInUser()?.let { user ->
                                ProfileScreen(
                                    userData = user,
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            authViewModel.resetSignInState()
                                            authViewModel.logout()
                                            if (authViewModel.getSignedInUser() == null) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Signed Out",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                            }
                                            navController.popBackStack()
                                        }
                                    },
                                    savedProfileUri = getSessionViewModel.session?.user?.profilePictureUrl,
                                    onNavigateToMenu = {
                                        navController.navigate(MenuScreen)
                                    }
                                )

                            }
                        }

                        composable<MenuScreen>{

                            MenuScreen(
                                onManualInput = {
                                    navController.navigate(ManualInputScreen)
                                },
                                onCameraInput = {
                                    navController.navigate(CameraScreen)
                                }
                            )
                        }

                        composable<ManualInputScreen>{


                        }
                        composable<CameraScreen> {


                            CameraScreen(
                                geminiAIViewModel = geminiAIViewModel,
                                navigateToResultsScreen = {
                                    navController.navigate(RecipeScreen)
                                }
                            )

                        }

                        composable<RecipeScreen>{
                            val geminiAIViewModel2 = hiltViewModel<GeminiAIViewModel2>()
                            geminiAIViewModel2.session?.let { session ->
                                RecipeScreen(
                                    session = session
                                )
                            }

                        }


                    }
                }
            }
        }
    }
    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )

    }
}

@Serializable
object SignInScreen

@Serializable
object SignUpScreen

@Serializable
object DashboardScreen

@Serializable
object GetStartedScreen

@Serializable
object CameraScreen


@Serializable
object MenuScreen


@Serializable
object ManualInputScreen


@Serializable
object RecipeScreen
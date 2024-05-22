package com.example.gharonkikahani.presentation.sign_in

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.theme.LightYellow
import com.example.gharonkikahani.uiStates.SignInUiStates

@Composable
fun SignInScreen(
    state: SignInUiStates,
//    saveSession: () -> Unit,
//    onEvent: (UserEvent) -> Unit,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInErrorMessage) {
        state.signInErrorMessage?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    GradientBackg(color = LightYellow) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(30.dp)
        ){


            item {

                Button(
                    onClick =
                )

                {
                    Text(text = "Sign in")
                }
            }
            item {
                IconButton(
                    onClick = onSignInClick
                ){
                    Icon(
                        painter = painterResource(id = com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark),
                        contentDescription = "Google Icon"
                    )
                    
                }
            }

        }
    }
}


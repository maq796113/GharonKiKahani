package com.example.gharonkikahani.presentation.sign_in

import SignInAction
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.components.GradientBackground
import com.example.gharonkikahani.ui.components.RuniqueTextField
import com.example.gharonkikahani.ui.theme.EmailIcon
import com.example.gharonkikahani.ui.theme.RuniqueWhite
import com.plcoding.composescreenshottesting.ui.login.components.RuniquePasswordTextField

@Composable
fun SignInScreen(
    state: SignInState,
//    saveSession: () -> Unit,
//    onEvent: (UserEvent) -> Unit,
    onAction: (SignInAction) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    GradientBackground(
        modifier = Modifier
            .padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(id = R.string.sign_in),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium,
                color = RuniqueWhite
            )

            Spacer(modifier = Modifier.height(48.dp))

            RuniqueTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = null,
                keyboardType = KeyboardType.Email,
                hint = stringResource(id = R.string.sample_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            RuniquePasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(SignInAction.OnTogglePasswordVisibility)
                },
                hint = "******ag",
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )


            IconButton(
                onClick = { onAction(SignInAction.OnGoogleLoginClick) }
            ){
                AsyncImage(
                    model = com.google.android.gms.base.R.drawable.googleg_standard_color_18,
                    contentDescription = "Google Icon",
                )


                

            }







        }
    }
}



@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        state = SignInState(),
        onAction = {}
    )
}
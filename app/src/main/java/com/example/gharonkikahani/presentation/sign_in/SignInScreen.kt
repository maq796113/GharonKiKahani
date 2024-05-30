package com.example.gharonkikahani.presentation.sign_in

import SignInAction
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.components.GradientBackground
import com.example.gharonkikahani.ui.components.RuniquePasswordTextField
import com.example.gharonkikahani.ui.components.RuniqueTextField
import com.example.gharonkikahani.ui.theme.EmailIcon
import com.example.gharonkikahani.ui.theme.quicksandFontFamily

@Composable
fun SignInScreen(
    state: SignInState,
    onAction: (SignInAction) -> Unit,
    onFieldInput: () -> Unit
) {
    val buttonSize = Modifier
        .height(35.dp)
        .fillMaxWidth()
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
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .padding(top = 16.dp)
        ){
            Text(
                text = stringResource(id = R.string.sign_in),
                fontWeight = FontWeight.SemiBold,
                fontFamily = quicksandFontFamily,
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
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
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(39.dp))

            RuniquePasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(SignInAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password_hint),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )



            //draw a horizontal line
            Spacer(modifier = Modifier.height(25.dp))

            ElevatedButton(
                onClick = {
                    onAction(SignInAction.OnSimpleLoginClick)
                },
                modifier = buttonSize
                ) {

                Text(text = "Login")
                if (state.isLoggingIn) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(
                                Alignment.CenterVertically
                            )
                            .size(5.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(25.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 2.dp,
                    modifier = Modifier
                        .width(115.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = "Or You Can Try",
                    modifier = Modifier
                        .align(Alignment.Top),
                    color = Color.Black

                )
                HorizontalDivider(
                    color = Color.Black,
                    thickness = 2.dp,
                    modifier = Modifier
                        .width(150.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            ElevatedButton(
                onClick = { onAction(SignInAction.OnGoogleLoginClick) },
                modifier = buttonSize
            ){
                Row(Modifier.fillMaxWidth()) {
                    Icon(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                        )
                    Text(
                        text = "Login with Google",
                        modifier = Modifier
                            .padding(
                                horizontal = 25.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                }

            }
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Don't have an account?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(200.dp),
                    color = Color.Black
                )
                TextButton(
                    onClick = { onAction(SignInAction.OnSignUpClick)},
                    modifier = Modifier

                ) {
                    Text(
                        text = "Register",
                        fontSize = 12.sp
                    )

                }

            }

        }
    }
}



@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        state = SignInState(),
        onAction = {},
        onFieldInput = {}
    )
}
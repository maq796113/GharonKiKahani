package com.example.gharonkikahani.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.components.GradientBackground
import com.example.gharonkikahani.ui.components.RuniquePasswordTextField
import com.example.gharonkikahani.ui.components.RuniqueTextField
import com.example.gharonkikahani.ui.theme.EmailIcon
import com.example.gharonkikahani.ui.theme.PersonIcon
import com.example.gharonkikahani.ui.theme.quicksandFontFamily

@Composable
fun SignUpScreen(
    signUpState: SignUpState,
    onAction: (SignUpAction) -> Unit
) {
    val space = Modifier.height(32.dp)
    val imagePicker = Modifier
        .clickable {
            onAction(SignUpAction.OnProfilePickerClick)
        }
    val context = LocalContext.current

    LaunchedEffect(key1 = signUpState.signUpError) {
        signUpState.signUpError?.let { error ->
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
                text = stringResource(id = R.string.sign_up),
                fontWeight = FontWeight.SemiBold,
                fontFamily = quicksandFontFamily,
                color = Color.White,
                fontSize = 45.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = space)


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(110.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(0.03f),
                        shape = CircleShape
                    )
                    .border(2.dp, Color.Black, CircleShape)
                    .padding(12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (signUpState.profilePicture == null) {
                    Image(
                        painter = painterResource(id = R.drawable.portrait),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = imagePicker


                    )
                } else {
                    AsyncImage(
                        model = signUpState.profilePicture.toUri(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = imagePicker
                    )

                }


            }

            Spacer(modifier = space)

            RuniqueTextField(
                state = signUpState.name,
                startIcon = PersonIcon,
                endIcon = null,
                keyboardType = KeyboardType.Text,
                hint = stringResource(id = R.string.sample_name),
                title = stringResource(id = R.string.name),
                modifier = Modifier
                    .fillMaxWidth()
            )


            Spacer(modifier = space)

            RuniqueTextField(
                state = signUpState.email,
                startIcon = EmailIcon,
                endIcon = null,
                keyboardType = KeyboardType.Email,
                hint = stringResource(id = R.string.sample_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = space)

            RuniquePasswordTextField(
                state = signUpState.password,
                isPasswordVisible = signUpState.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(SignUpAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password_hint),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )




            Spacer(modifier = space)


            RuniquePasswordTextField(
                state = signUpState.confirmPassword,
                isPasswordVisible = signUpState.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(SignUpAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password_hint),
                title = stringResource(id = R.string.re_type_password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = space)

            ElevatedButton(
                onClick = {

                    onAction(SignUpAction.OnRegisterClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }

            Spacer(modifier = space)



            HorizontalDivider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )




            Spacer(modifier = space)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Already have an account?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(200.dp),
                    color = Color.Black
                )
                TextButton(
                    onClick = { onAction(SignUpAction.OnLoginClick)},
                    modifier = Modifier

                ) {
                    Text(
                        text = "Log In",
                        fontSize = 12.sp
                    )

                }

            }

        }
    }
}



@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        signUpState = SignUpState(),
        onAction = {}
    )
}





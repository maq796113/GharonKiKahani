package com.example.gharonkikahani.presentation.get_started

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.RootGroupName
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.theme.LightYellow
import com.example.gharonkikahani.ui.theme.PurpleGrey40
import com.example.gharonkikahani.ui.theme.WeirdOrange
import com.example.gharonkikahani.ui.theme.poppinsFontFamily
import com.example.gharonkikahani.ui.theme.quicksandFontFamily
import com.example.gharonkikahani.ui.theme.spiraxFontFamily

@Composable
fun GetStartedScreen(
    onGettingStarted: () -> Unit
) {
    Surface(color = LightYellow) {
        val imageModifier = Modifier
            .size(
                width = 380.dp,
                height = 366.dp
            )
        val padding = Modifier
            .padding(40.dp)
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(30.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.platter),
                    contentDescription = "Platter of food",
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit
                )
            }
            item {
                Text(
                    text = "Welcome To Gharon Ki Kahani \uD83C\uDF73",
                    fontFamily = spiraxFontFamily,
                    color = WeirdOrange,
                    lineHeight = 45.sp,
                    fontSize = 46.sp,
                    textAlign = TextAlign.Center,
                    modifier = padding
                )
            }
            item {
                Text(
                    text = "Become a Pakistani cooking pro: We make it simple and delicious! \uD83E\uDD58",
                    fontFamily = quicksandFontFamily,
                    color = PurpleGrey40,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            item {
                ElevatedButton(
                    onClick = onGettingStarted,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WeirdOrange
                    ),
                    modifier = padding
                        .size(
                            width = 324.dp,
                            height = 58.dp
                        )
                ) {
                    Text(
                        text = "Get Started",
                        fontFamily = poppinsFontFamily,
                        color = Color.White,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                }
            }



        }

    }

}
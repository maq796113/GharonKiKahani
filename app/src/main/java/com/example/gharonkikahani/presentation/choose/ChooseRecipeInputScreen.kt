package com.example.gharonkikahani.presentation.choose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.R

@Composable
fun ChooseRecipeInputScreen(
    onManualInput: () -> Unit,
    onCaptureInput: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7CD)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OptionCard(
            text = "Add Ingredients\nManually",
            icon = painterResource(id = R.drawable.ic_text), // Replace with your text icon
            onClick = {onManualInput()}

        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "OR",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OptionCard(
            text = "Capture your\nIngredients",
            icon = painterResource(id = R.drawable.ic_camera), // Replace with your camera icon
            onClick = {onCaptureInput()}
        )
    }
}

@Composable
fun OptionCard(text: String, icon: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(Color(0xFFFFE6E6), shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChooseRecipeInputScreenPreview() {
//    chooseRecipeInputScreen()
//}

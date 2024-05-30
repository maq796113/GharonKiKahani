package com.example.gharonkikahani.presentation.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onManualInput: () -> Unit,
    onCameraInput: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onManualInput) {
            Text("Manual")
        }
        Spacer(
            modifier = Modifier.height(25.dp)
        )
        Button(onClick = onCameraInput) {
            Text("Camera")
        }

    }



}
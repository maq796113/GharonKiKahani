package com.example.gharonkikahani.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.R
import com.example.gharonkikahani.ui.theme.poppinsFontFamily

/**
 * This composable was generated from the UI Package 'capture_your_ingredients'.
 * Generated code; do not edit directly
 *
 * @param onOpenCamera CameraScreen is opened
 */
@Composable
fun CaptureYourIngredients(
    modifier: Modifier = Modifier,
    onOpenCamera: () -> Unit = {}
) {
    TopLevel(
        onOpenCamera = onOpenCamera,
        modifier = modifier
    ) {
        Rectangle7(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
        CameraTakingPhotoWithAFlash(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
        Ellipse3(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
        CaptureYourIngredients(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
        Image8(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
    }
}

@Preview
@Composable
private fun CaptureYourIngredientsPreview() {
    MaterialTheme {
        RelayContainer {
            CaptureYourIngredients(
                onOpenCamera = {},
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun Rectangle7(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.orange_square),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 57.0.dp,
                top = 74.0.dp,
                end = 0.0.dp,
                bottom = 0.000091552734375.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun CameraTakingPhotoWithAFlash(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.fancy_camera),
        contentScale = ContentScale.Fit,
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 14.87646484375.dp,
                top = 14.87646484375.dp,
                end = 142.12353515625.dp,
                bottom = 159.12362670898438.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Ellipse3(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.white_circle),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 112.0.dp,
                top = 99.0.dp,
                end = 55.0.dp,
                bottom = 85.00009155273438.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun CaptureYourIngredients(modifier: Modifier = Modifier) {
    RelayText(
        content = "Capture Your\nIngredients",
        fontSize = 20.0.sp,
        fontFamily = poppinsFontFamily,
        color = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.5.em,
        fontWeight = FontWeight(700.0.toInt()),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 88.0.dp,
                top = 194.0.dp,
                end = 31.0.dp,
                bottom = 20.000091552734375.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Image8(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.camera_icon),
        contentScale = ContentScale.Crop,
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 122.0.dp,
                top = 105.0.dp,
                end = 64.0.dp,
                bottom = 100.00009155273438.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun TopLevel(
    onOpenCamera: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onOpenCamera).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

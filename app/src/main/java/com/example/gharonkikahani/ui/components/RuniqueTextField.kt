package com.example.gharonkikahani.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharonkikahani.ui.theme.BlackForTextFields
import com.example.gharonkikahani.ui.theme.RuniqueDarkGray
import com.example.gharonkikahani.ui.theme.RuniqueDarkRed
import com.example.gharonkikahani.ui.theme.RuniqueGray
import com.example.gharonkikahani.ui.theme.RuniqueGray40
import com.example.gharonkikahani.ui.theme.RuniqueGreen
import com.example.gharonkikahani.ui.theme.RuniqueGreen5
import com.example.gharonkikahani.ui.theme.RuniqueWhite
import com.example.gharonkikahani.ui.theme.quicksandFontFamily
import com.example.gharonkikahani.ui.theme.rainbowColors


@Composable
fun RuniqueTextField(
    state: TextFieldState,
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    hint: String,
    title: String?,
    modifier: Modifier = Modifier,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    additionalInfo: String? = null,
) {
    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = BlackForTextFields,
                    fontFamily = quicksandFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
            if(error != null) {
                Text(
                    text = error,
                    color = RuniqueDarkRed,
                    fontSize = 12.sp,
                    fontFamily = quicksandFontFamily
                )
            } else if (additionalInfo != null) {
                Text(
                    text = additionalInfo,
                    color = RuniqueGray,
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))


        val brush = remember {
            Brush.linearGradient(
                colors = rainbowColors
            )
        }

        BasicTextField(
            state = state,
            textStyle = TextStyle(brush=brush),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            decorator = { innerBox ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = RuniqueGray
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = RuniqueGray40,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }

                    if (endIcon != null) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Icon(
                            imageVector = endIcon,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            cursorBrush = SolidColor(RuniqueWhite),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    when {
                        isFocused -> RuniqueGreen5
                        else -> RuniqueDarkGray
                    }
                )
                .border(
                    width = 1.dp,
                    color = when {
                        isFocused -> RuniqueGreen
                        else -> Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                }
        )
    }
}

@Preview
@Composable
fun RuniqueTextFieldPreview() {
    RuniqueTextField(
        state = TextFieldState("English"),
        startIcon = null,
        endIcon = null,
        hint = "Hint",
        title = "Title",
        error = null,
    )
}
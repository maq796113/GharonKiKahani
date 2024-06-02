package com.example.gharonkikahani.states

import androidx.compose.foundation.text.input.TextFieldState

data class IngredientState(
    val name: TextFieldState = TextFieldState(),
    val amount: TextFieldState = TextFieldState(),
    val unit: TextFieldState = TextFieldState(),

)

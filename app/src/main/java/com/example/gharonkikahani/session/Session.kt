package com.example.gharonkikahani.session

import com.example.gharonkikahani.data.User
import com.example.gharonkikahani.uiStates.GeminiAIUiState

data class Session(
    val geminiAIUiState: GeminiAIUiState?,
    val user: User?
)
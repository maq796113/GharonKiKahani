package com.example.gharonkikahani.presentation.manual_input

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import com.example.gharonkikahani.states.IngredientState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ManualScreen(
    state: IngredientState
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()


}
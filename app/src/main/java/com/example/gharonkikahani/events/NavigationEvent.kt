package com.example.gharonkikahani.events

sealed interface NavigationEvent {
    object NavigateToLogin : NavigationEvent
    object NavigateToHome : NavigationEvent

    object NavigateToRegister : NavigationEvent

    object NavigateToRecipe : NavigationEvent

}
package com.example.gharonkikahani.presentation.sign_up

sealed interface SignUpAction {
    data object OnRegisterClick: SignUpAction
    data object OnLoginClick: SignUpAction

    data object OnTogglePasswordVisibility: SignUpAction

    data object OnProfilePickerClick: SignUpAction



}
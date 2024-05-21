package com.example.gharonkikahani.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gharonkikahani.presentation.splash.SplashScreenReadyState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {
    var isReady by mutableStateOf(SplashScreenReadyState())
        private set

    init {
        viewModelScope.launch {
            delay(2000L)
            isReady = isReady.copy(
                value = true
            )
        }
    }


}
package com.example.gharonkikahani.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharonkikahani.BuildConfig

import com.example.gharonkikahani.session.Session
import com.example.gharonkikahani.session.SessionCache
import com.example.gharonkikahani.states.BitmapState
import com.example.gharonkikahani.uiStates.GeminiAIUiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GeminiAIViewModel1 @Inject constructor(
    private val sessionCache: SessionCache
): ViewModel() {

    private val _uiState: MutableStateFlow<GeminiAIUiState> = MutableStateFlow(GeminiAIUiState())
    val uiState = _uiState.asStateFlow()

    private val _bitmapState: MutableStateFlow<BitmapState> = MutableStateFlow(BitmapState())
    val bitmapState = _bitmapState.asStateFlow()

    private var generativeModelImg: GenerativeModel
    private var generativeModelTxt: GenerativeModel

    sealed class MyEvent {

        data class LoadingEvent(val isLoading: Boolean): MyEvent()
        data class SuccessEvent(val isTrue: Boolean): MyEvent()
    }

    private val eventChannel = Channel<MyEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun triggerEvent() = viewModelScope.launch {
        uiState.value.isLoading?.let { MyEvent.LoadingEvent(it) }?.let { eventChannel.send(it) }
        eventChannel.send(MyEvent.SuccessEvent(uiState.value.isSuccessful))
    }

    fun saveSession() {
        sessionCache.saveSession(
            session = Session(
                geminiAIUiState = uiState.value,
                user = null
            )
        )
    }

    init {
        val config = generationConfig {
            temperature = 0.50f
        }

        generativeModelImg = GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = BuildConfig.apiKey,
            generationConfig = config
        )
        generativeModelTxt = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = BuildConfig.apiKey,
            generationConfig = config
        )
    }
    fun getSelectedBitmap(bitmapUri: String)  {
        _bitmapState.value = bitmapState.value.copy(
            bitmapUri = bitmapUri
        )
    }
    fun initializeLoading() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    fun promptImg(selectedImage: Bitmap) {
        val prompt = "Given these ingredients in the image, provide me a precise and concise recipe from Pakistani cuisine that can be easily made from the aforementioned ingredients. Don't forget to mention ingredients, method, serving size, measurements and the time it takes to cook."

        viewModelScope.launch {
            try {
                val content = content {
                    image(
                        selectedImage
                    )
                    text(
                        prompt
                    )
                }
                var output = ""
                generativeModelImg.generateContentStream(content).collect {
                    output += it.text
                    withContext(Dispatchers.Main.immediate) {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            response = output,
                            isSuccessful = true
                        )

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        response = e.message
                    )
                }
            }
        }
    }

    //need to add more parameters for refining
    fun promptText(ingredients: List<String>) {
        val prompt = "Given I have $ingredients, provide me a precise and concise recipe from Pakistani cuisine that can be easily made from the aforementioned ingredients. Don't forget to mention ingredients, method, serving size, measurements and the time it takes to cook."

        viewModelScope.launch {
            try {
                val content = content {
                    text(
                        prompt
                    )
                }
                var output = ""
                generativeModelTxt.generateContentStream(content).collect {
                    output += it.text
                    withContext(Dispatchers.Main.immediate) {
                        _uiState.value = uiState.value.copy(
                            isLoading = false,
                            response = output,
                            isSuccessful = true
                        )

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main.immediate) {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        response = e.message
                    )
                }
            }
        }
    }
}
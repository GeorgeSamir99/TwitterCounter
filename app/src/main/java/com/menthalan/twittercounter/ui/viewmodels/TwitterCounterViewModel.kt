package com.menthalan.twittercounter.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.menthalan.twittercounter.domain.usecase.CountTwitterCharactersUseCase
import com.menthalan.twittercounter.domain.usecase.GetRemainingCharactersUseCase
import com.menthalan.twittercounter.domain.usecase.TwitterPostUseCase
import com.menthalan.twittercounter.domain.usecase.ValidateTwitterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwitterCounterViewModel  @Inject constructor(
    private val twitterPostUseCase: TwitterPostUseCase,
    private val countUseCase: CountTwitterCharactersUseCase,
    private val remainingUseCase: GetRemainingCharactersUseCase,
    private val validateUseCase: ValidateTwitterUseCase
): ViewModel(){

    private val _uiState = MutableStateFlow(TweetUiState())
    val uiState: StateFlow<TweetUiState> = _uiState.asStateFlow()

    fun onTextChanged(newText: String) {
        val typed = countUseCase(newText)
        val remaining = remainingUseCase(newText)
        _uiState.value = _uiState.value.copy(
            text = newText,
            typed = typed,
            remaining = remaining,
            isOverLimit = remaining < 0,
            isPostEnabled = validateUseCase(newText)
        )
    }
    fun postTweet() {
        val text = _uiState.value.text ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val result = twitterPostUseCase(text)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                postSuccess = result.isSuccess
            )
        }
    }

    fun clearText() = onTextChanged("")

    fun copyText(): String = _uiState.value.text ?: ""


    fun consumePostResult() {
        _uiState.value = _uiState.value.copy(postSuccess = false)
    }
}

data class TweetUiState(
    val text: String = "",
    val typed: Int = 0,
    val remaining: Int = 280,
    val isOverLimit: Boolean = false,
    val isPostEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val postSuccess: Boolean = false,

)
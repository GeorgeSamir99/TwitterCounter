package com.menthalan.twittercounter.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.menthalan.twittercounter.data.TwitterRepository
import com.menthalan.twittercounter.domain.usecase.CountTweetCharactersUseCase
import com.menthalan.twittercounter.domain.usecase.GetRemainingCharactersUseCase
import com.menthalan.twittercounter.domain.usecase.ValidateTweetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TwitterCounterViewModel : ViewModel(){

    private val _uiState = MutableStateFlow(TweetUiState())
    val uiState: StateFlow<TweetUiState> = _uiState.asStateFlow()
    private val repository = TwitterRepository()
    private val countUseCase = CountTweetCharactersUseCase()
    private val remainingUseCase = GetRemainingCharactersUseCase()
    private val validateUseCase = ValidateTweetUseCase()
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
            val result = repository.postTweet(text)
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
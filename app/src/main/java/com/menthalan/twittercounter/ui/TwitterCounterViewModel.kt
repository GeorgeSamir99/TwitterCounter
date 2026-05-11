package com.menthalan.twittercounter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menthalan.twitter_counter_utils.TwitterCharacterCounter
import com.menthalan.twittercounter.data.TwitterRepository
import kotlinx.coroutines.launch

class TwitterCounterViewModel : ViewModel(){

    private val _uiState = MutableLiveData(TweetUiState())
    val uiState: LiveData<TweetUiState> = _uiState
    private val repository = TwitterRepository()
    fun onTextChanged(newText: String) {
        val typed = TwitterCharacterCounter.count(newText)
        val remaining = TwitterCharacterCounter.remaining(newText)
        _uiState.value = TweetUiState(
            text = newText,
            typed = typed,
            remaining = remaining,
            isOverLimit = remaining < 0,
            isPostEnabled = TwitterCharacterCounter.isValid(newText)
        )
    }
    fun postTweet() {
        val text = _uiState.value?.text ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value?.copy(isLoading = true)
            val result = repository.postTweet(text)
            _uiState.value = _uiState.value?.copy(
                isLoading = false,
                postSuccess = result.isSuccess
            )
        }
    }

    fun clearText() = onTextChanged("")

    fun copyText(): String = _uiState.value?.text ?: ""
}

data class TweetUiState(
    val text: String = "",
    val typed: Int = 0,
    val remaining: Int = TwitterCharacterCounter.MAX_CHARACTERS,
    val isOverLimit: Boolean = false,
    val isPostEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val postSuccess: Boolean = false,

)
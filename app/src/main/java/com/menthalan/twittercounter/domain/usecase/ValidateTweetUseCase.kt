package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twitter_counter_utils.TwitterCharacterCounter
import javax.inject.Inject

class ValidateTweetUseCase @Inject constructor(){
    operator fun invoke(text: String): Boolean {
        return TwitterCharacterCounter.isValid(text)
    }
}
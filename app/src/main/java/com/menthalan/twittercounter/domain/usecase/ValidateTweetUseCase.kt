package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twitter_counter_utils.TwitterCharacterCounter

class ValidateTweetUseCase {
    operator fun invoke(text: String): Boolean {
        return TwitterCharacterCounter.isValid(text)
    }
}
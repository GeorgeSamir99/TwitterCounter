package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twitter_counter_utils.TwitterCharacterCounter

class CountTweetCharactersUseCase {
    operator fun invoke(text: String): Int {
        return TwitterCharacterCounter.count(text)
    }
}
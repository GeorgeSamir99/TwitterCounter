package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twitter_counter_utils.TwitterCharacterCounter
import javax.inject.Inject


class GetRemainingCharactersUseCase @Inject constructor(){
    operator fun invoke(text: String): Int {
        return TwitterCharacterCounter.remaining(text)
    }
}
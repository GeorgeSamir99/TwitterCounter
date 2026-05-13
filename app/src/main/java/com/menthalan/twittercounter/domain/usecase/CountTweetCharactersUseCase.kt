package com.menthalan.twittercounter.domain.usecase


import com.menthalan.twittercounter.domain.repo.TwitterRepo
import javax.inject.Inject

class CountTweetCharactersUseCase  @Inject constructor(
    private val twitterRepo: TwitterRepo
) {
    operator fun invoke(text: String): Int {
        return twitterRepo.count(text)
    }
}
package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twittercounter.domain.repo.TwitterRepo
import javax.inject.Inject

class TwitterPostUseCase @Inject constructor(
    private val twitterRepo: TwitterRepo
){
    suspend operator fun invoke(text: String): Result<String> {
        return twitterRepo.postTweet(text)
    }
}
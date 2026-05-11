package com.menthalan.twittercounter.data

import kotlinx.coroutines.delay

class TwitterRepository {
    suspend fun postTweet(text: String): Result<String> {

        delay(1000)
        return Result.success("Tweet posted successfully")
    }
}
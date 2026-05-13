package com.menthalan.twittercounter.domain.repo

interface TwitterRepo  {

    fun count(text: String): Int
    fun remaining(text: String): Int
    fun isValid(text: String): Boolean
    suspend fun postTweet(text: String): Result<String>
}
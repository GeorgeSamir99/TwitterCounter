package com.menthalan.twittercounter.data.repo

import com.menthalan.twittercounter.domain.repo.TwitterRepo
import kotlinx.coroutines.delay
import javax.inject.Inject

class TwitterRepoImpl @Inject constructor() : TwitterRepo {
    private  val MAX_CHARACTERS = 280
    private val URL_LENGTH = 23
    private val URL_REGEX = Regex("""https?://\S+""")
    private fun String.unicodeLength(): Int = codePointCount(0, length)
    override fun count(text: String): Int {
        if (text.isEmpty()) return 0

        var weightedLength = 0
        var lastEnd = 0

        for (match in URL_REGEX.findAll(text)) {
            weightedLength += text.substring(lastEnd, match.range.first).unicodeLength()
            weightedLength += URL_LENGTH
            lastEnd = match.range.last + 1
        }

        if (lastEnd < text.length) {
            weightedLength += text.substring(lastEnd).unicodeLength()
        }

        return weightedLength
    }

    override fun remaining(text: String): Int =MAX_CHARACTERS - count(text)

    override fun isValid(text: String): Boolean = text.isNotBlank() && count(text) <= MAX_CHARACTERS
    override suspend fun postTweet(text: String): Result<String> {
        delay(1000)
        return Result.success("Tweet posted successfully")
    }
}
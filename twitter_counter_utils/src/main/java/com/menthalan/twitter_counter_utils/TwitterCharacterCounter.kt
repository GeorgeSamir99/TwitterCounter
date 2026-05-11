package com.menthalan.twitter_counter_utils

object  TwitterCharacterCounter {
    const val MAX_CHARACTERS = 280
    private const val URL_LENGTH = 23
    private val URL_REGEX = Regex("""https?://\S+""")

    fun count(text: String): Int {
        if (text.isEmpty()) return 0

        var weightedLength = 0
        var lastEnd = 0

        for (match in URL_REGEX.findAll(text)) {
            weightedLength += text.substring(lastEnd, match.range.first).codePointCount(0, match.range.first - lastEnd)
            weightedLength += URL_LENGTH
            lastEnd = match.range.last + 1
        }

        if (lastEnd < text.length) {
            val remaining = text.substring(lastEnd)
            weightedLength += remaining.codePointCount(0, remaining.length)
        }

        return weightedLength
    }

    fun remaining(text: String): Int = MAX_CHARACTERS - count(text)

    fun isValid(text: String): Boolean = text.isNotBlank() && count(text) <= MAX_CHARACTERS

}
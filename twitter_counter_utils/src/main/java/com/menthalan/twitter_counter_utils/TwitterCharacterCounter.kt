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
            weightedLength += text.substring(lastEnd, match.range.first).unicodeLength()
            weightedLength += URL_LENGTH
            lastEnd = match.range.last + 1
        }

        if (lastEnd < text.length) {
            weightedLength += text.substring(lastEnd).unicodeLength()
        }

        return weightedLength
    }

    fun remaining(text: String): Int = MAX_CHARACTERS - count(text)

    fun isValid(text: String): Boolean = text.isNotBlank() && count(text) <= MAX_CHARACTERS


    private fun String.unicodeLength(): Int = codePointCount(0, length)
}
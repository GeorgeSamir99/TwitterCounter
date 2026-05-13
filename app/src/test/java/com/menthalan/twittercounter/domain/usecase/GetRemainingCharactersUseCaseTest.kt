package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twittercounter.domain.repo.TwitterRepo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class GetRemainingCharactersUseCaseTest {
    private lateinit var twitterRepo: TwitterRepo
    private lateinit var useCase: GetRemainingCharactersUseCase

    @Before
    fun setup() {
        twitterRepo = mock()
        useCase = GetRemainingCharactersUseCase(twitterRepo)
    }

    @Test
    fun `invoke should return correct remaining characters`() {

        val input = "Hello"
        val expectedRemaining = 275

        whenever(twitterRepo.remaining(input)).thenReturn(expectedRemaining)


        val result = useCase(input)


        assertEquals(expectedRemaining, result)
    }

    @Test
    fun `invoke should return full limit when text is empty`() {

        val input = ""
        val expected = 280

        whenever(twitterRepo.remaining(input)).thenReturn(expected)
        val result = useCase(input)

        assertEquals(expected, result)

}
}
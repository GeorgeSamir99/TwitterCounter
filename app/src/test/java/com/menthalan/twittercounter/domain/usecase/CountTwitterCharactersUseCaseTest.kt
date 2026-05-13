package com.menthalan.twittercounter.domain.usecase

import com.menthalan.twittercounter.domain.repo.TwitterRepo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CountTwitterCharactersUseCaseTest {
    private lateinit var twitterRepo: TwitterRepo
    private lateinit var useCase: CountTwitterCharactersUseCase

    @Before
    fun setup() {
        twitterRepo = mock()
        useCase = CountTwitterCharactersUseCase(twitterRepo)
    }

    @Test
    fun `invoke should return correct count from repository`() {
        val input = "Hello Twitter"
        val expectedCount = 13
        whenever(twitterRepo.count(input)).thenReturn(expectedCount)
        val result = useCase(input)
        assertEquals(expectedCount, result)
    }

    @Test
    fun `invoke should return zero for empty string`() {
        val input = ""
        val expected = 0

        whenever(twitterRepo.count(input)).thenReturn(expected)

        val result = useCase(input)

        assertEquals(expected, result)
    }

    @Test
    fun `invoke should count spaces correctly`() {
        val input = "a b c"
        val expected = 5

        whenever(twitterRepo.count(input)).thenReturn(expected)

        val result = useCase(input)

        assertEquals(expected, result)
    }
}
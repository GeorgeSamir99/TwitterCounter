package com.menthalan.twittercounter.domain.usecase


import com.menthalan.twittercounter.domain.repo.TwitterRepo
import javax.inject.Inject

class ValidateTwitterUseCase @Inject constructor(
    private val twitterRepo: TwitterRepo
){
    operator fun invoke(text: String): Boolean {
        return twitterRepo.isValid(text)
    }
}
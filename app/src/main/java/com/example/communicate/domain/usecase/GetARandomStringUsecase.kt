package com.example.communicate.domain.usecase

import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.DataError
import com.example.communicate.util.Result
import javax.inject.Inject

class GetARandomStringUsecase @Inject constructor(private val repository: RandomStringRepository) {
    suspend operator fun invoke(length: Int): Result<RandomString, DataError.Local> =
        repository.getNewRandomString(length)
}
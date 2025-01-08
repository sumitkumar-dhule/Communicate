package com.example.communicate.domain.usecase

import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.DataError
import com.example.communicate.util.Result
import javax.inject.Inject

class GetAllRandomStringsUsecase @Inject constructor(private val repository: RandomStringRepository) {
    suspend operator fun invoke(): Result<List<RandomString>, DataError.Local>  =
        repository.getAllRandomString()
}
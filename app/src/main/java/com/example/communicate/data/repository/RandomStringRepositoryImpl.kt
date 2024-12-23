package com.example.communicate.data.repository

import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.DataError
import com.example.communicate.util.RandomStringContentResolver
import com.example.communicate.util.Result
import com.example.communicate.util.asEmptyDataResult
import javax.inject.Inject

class RandomStringRepositoryImpl @Inject constructor(
    private val contentResolver: RandomStringContentResolver,
    private val mapper: RandomStringMapper
) : RandomStringRepository {
    override suspend fun getNewRandomString(length: Int): Result<RandomString, DataError.Local> {
        return when (val result = contentResolver.getResponseFromProvider(length)) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(mapper.map(result.data))
        }
    }

    override suspend fun saveNewRandomString(save: RandomString) {
        TODO("Not yet implemented")
    }

    override suspend fun removeString(save: RandomString) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRandomString(): List<RandomString> {
        TODO("Not yet implemented")
    }

    override suspend fun clearAll() {
        TODO("Not yet implemented")
    }
}
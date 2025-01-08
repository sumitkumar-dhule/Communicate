package com.example.communicate.domain.repository

import com.example.communicate.domain.model.RandomString
import com.example.communicate.util.DataError
import com.example.communicate.util.Result

interface RandomStringRepository {
    suspend fun getNewRandomString(length: Int): Result<RandomString, DataError.Local>
    suspend fun saveNewRandomString(randomString: RandomString)
    suspend fun removeString(randomString: RandomString)
    suspend fun getAllRandomString(): List<RandomString>
    suspend fun clearAll()
}
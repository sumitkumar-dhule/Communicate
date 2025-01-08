package com.example.communicate.domain.repository

import com.example.communicate.domain.model.RandomString
import com.example.communicate.util.DataError
import com.example.communicate.util.Result

interface RandomStringRepository {
    suspend fun getNewRandomString(length: Int): Result<Unit, DataError.Local>
    suspend fun removeString(id: Int): Result<Unit, DataError.Local>
    suspend fun getAllRandomString(): Result<List<RandomString>, DataError.Local>
    suspend fun clearAll()
}
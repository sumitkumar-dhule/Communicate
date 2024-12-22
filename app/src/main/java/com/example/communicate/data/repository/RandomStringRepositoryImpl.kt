package com.example.communicate.data.repository

import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.RandomStringContentResolver
import javax.inject.Inject

class RandomStringRepositoryImpl @Inject constructor(
    private val contentResolver: RandomStringContentResolver,
    private val mapper: RandomStringMapper): RandomStringRepository {
    override suspend fun getNewRandomString(length: Int): RandomString {
     return  mapper.map(contentResolver.getStringFromProvider(length))
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
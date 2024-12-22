package com.example.communicate.domain.repository

import com.example.communicate.domain.model.RandomString

interface RandomStringRepository {
    suspend fun getNewRandomString(): RandomString
    suspend fun saveNewRandomString(save: RandomString)
    suspend fun getAllRandomString(): List<RandomString>
}
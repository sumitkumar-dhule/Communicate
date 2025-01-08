package com.example.communicate.data.repository

import com.example.communicate.data.local.RandomStringDao
import com.example.communicate.data.local.RandomStringEntity
import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.DataError
import com.example.communicate.util.RandomStringContentResolver
import com.example.communicate.util.Result
import javax.inject.Inject

class RandomStringRepositoryImpl @Inject constructor(
    private val contentResolver: RandomStringContentResolver,
    private val dao: RandomStringDao,
    private val mapper: RandomStringMapper
) : RandomStringRepository {
    override suspend fun getNewRandomString(length: Int): Result<RandomString, DataError.Local> {
        return when (val result = contentResolver.getResponseFromProvider(length)) {
            is Result.Error -> Result.Error(result.error)
            is Result.Success -> Result.Success(mapper.map(result.data)) //add to db // retun success Empty
        }
    }

    override suspend fun saveNewRandomString(randomString: RandomString) {
        dao.insert(
            RandomStringEntity(
                value = randomString.value,
                length = randomString.length,
                created = randomString.created
            )
        )
    }

    override suspend fun removeString(randomString: RandomString) {
        dao.deleteSpecificStringById(randomString.id)
    }

    override suspend fun getAllRandomString(): List<RandomString> {
        return dao.getAllRandomStrings().map {
            RandomString(
                id = it.id,
                value = it.value,
                length = it.length,
                created = it.created
            )
        }
    }

    override suspend fun clearAll() {
        dao.deleteAllStrings()
    }
}

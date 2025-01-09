package com.example.communicate.data.repository

import com.example.communicate.data.local.RandomStringDao
import com.example.communicate.data.local.RandomStringEntity
import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.DataError
import com.example.communicate.util.RandomStringContentResolver
import com.example.communicate.util.Result
import com.example.communicate.util.Result.Error
import com.example.communicate.util.Result.Success
import javax.inject.Inject

class RandomStringRepositoryImpl @Inject constructor(
    private val contentResolver: RandomStringContentResolver,
    private val dao: RandomStringDao,
    private val mapper: RandomStringMapper
) : RandomStringRepository {
    override suspend fun getNewRandomString(length: Int): Result<Unit, DataError.Local> {
        return when (val result = contentResolver.getResponseFromProvider(length)) {
            is Error -> Error(result.error)
            is Success -> {
                insertStringToDatabase(mapper.map(result.data))
                return Success(Unit)
            }
        }
    }

    private suspend fun insertStringToDatabase(randomString: RandomString) {
        dao.insert(
            RandomStringEntity(
                value = randomString.value,
                length = randomString.length,
                created = randomString.created
            )
        )
    }

    override suspend fun removeString(id: Int): Result<Unit, DataError.Local> {
        try {
            dao.deleteSpecificStringById(id)
            return Success(Unit)
        } catch (exception: Exception) {
            return Error(error = DataError.Local.DATABASE_ERROR)
        }
    }

    override suspend fun getAllRandomString(): Result<List<RandomString>, DataError.Local> {
        try {
            val string = dao.getAllRandomStrings().map {
                RandomString(
                    id = it.id,
                    value = it.value,
                    length = it.length,
                    created = it.created
                )
            }

            return Success(data = string)
        } catch (exception: Exception) {
            return Error(error = DataError.Local.DATABASE_ERROR)
        }
    }

    override suspend fun clearAll() {
        dao.deleteAllStrings()
    }
}

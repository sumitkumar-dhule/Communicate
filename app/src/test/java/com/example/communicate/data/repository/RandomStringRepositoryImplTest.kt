package com.example.communicate.data.repository

import com.example.communicate.data.local.RandomStringDao
import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.domain.model.RandomString
import com.example.communicate.util.RandomStringContentResolver
import com.example.communicate.util.Result
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class RandomStringRepositoryImplTest {

    private lateinit var stringRepositoryImpl: RandomStringRepositoryImpl

    @MockK
    private lateinit var contentResolver: RandomStringContentResolver

    @MockK
    private lateinit var dao: RandomStringDao

    @MockK
    private lateinit var mapper: RandomStringMapper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        stringRepositoryImpl = RandomStringRepositoryImpl(contentResolver, dao, mapper)
    }

    @Test
    fun `when string is requested then return success and add string to db`() = runTest {
        val randomString: RandomString = mockk(relaxed = true)
        val strLength = 5
        val jsonResponse = "jsonResponse"
        coEvery { contentResolver.getResponseFromProvider(strLength) } returns Result.Success(data = jsonResponse)
        coEvery { dao.insert(any()) } returns Unit
        every { mapper.map(jsonResponse) } returns randomString
        val result = stringRepositoryImpl.getNewRandomString(strLength)
        expectThat(result).isEqualTo(Result.Success(Unit))
        coEvery { dao.insert(any()) }
    }

}
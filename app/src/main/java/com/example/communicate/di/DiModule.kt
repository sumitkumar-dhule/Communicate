package com.example.communicate.di

import android.content.Context
import com.example.communicate.data.local.DatabaseInstance
import com.example.communicate.data.local.RandomStringDao
import com.example.communicate.data.mapper.RandomStringMapper
import com.example.communicate.data.repository.RandomStringRepositoryImpl
import com.example.communicate.domain.repository.RandomStringRepository
import com.example.communicate.util.RandomStringContentResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    internal fun provideContentResolver(
        @ApplicationContext appContext: Context
    ): RandomStringContentResolver =
        RandomStringContentResolver(appContext)

    @Provides
    @Singleton
    internal fun provideDao(
        @ApplicationContext appContext: Context
    ): RandomStringDao = DatabaseInstance.getDatabase(appContext).randomStringDao()


    @Provides
    fun provideRepo(
        randomStringContentResolver: RandomStringContentResolver,
        mapper: RandomStringMapper,
        dao: RandomStringDao
    ): RandomStringRepository =
        RandomStringRepositoryImpl(randomStringContentResolver, dao, mapper)



}
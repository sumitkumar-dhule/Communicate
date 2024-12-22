package com.example.communicate.di

import android.content.Context
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
    fun provideRepo(
        randomStringContentResolver: RandomStringContentResolver,
        mapper: RandomStringMapper
    ): RandomStringRepository =
        RandomStringRepositoryImpl(randomStringContentResolver, mapper)

}
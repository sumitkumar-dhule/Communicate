package com.example.communicate.domain.usecase

import com.example.communicate.domain.repository.RandomStringRepository
import javax.inject.Inject

class ClearAllUsecase @Inject constructor(private val repository: RandomStringRepository) {
    suspend operator fun invoke() = repository.clearAll()
}
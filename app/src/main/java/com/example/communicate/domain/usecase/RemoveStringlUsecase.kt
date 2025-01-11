package com.example.communicate.domain.usecase

import com.example.communicate.domain.repository.RandomStringRepository
import javax.inject.Inject

class RemoveStringlUsecase @Inject constructor(private val repository: RandomStringRepository) {
    suspend operator fun invoke(stringId: Int) = repository.removeString(stringId)
}
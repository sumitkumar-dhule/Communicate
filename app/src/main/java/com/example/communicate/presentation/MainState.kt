package com.example.communicate.presentation

import androidx.compose.runtime.Immutable
import com.example.communicate.domain.model.RandomString

data class MainState(
    val stringList: List<RandomString> = listOf(),
    val isLoading: Boolean = false,
    val hasElements: Boolean = false,
    val isError: Boolean = false
)

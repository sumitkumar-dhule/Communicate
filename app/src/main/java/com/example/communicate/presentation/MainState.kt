package com.example.communicate.presentation

import com.example.communicate.domain.model.RandomString

data class MainState(
    val aRandomSting: RandomString,
    val isLoading: Boolean = false,
    val hasElements: Boolean = false,
    val isError: Boolean = false
)

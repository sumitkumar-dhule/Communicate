package com.example.communicate.presentation

import com.example.communicate.domain.model.RandomString

sealed class MainUiEvent {
    data class NavigateToDetailsScreen(val randomString: RandomString): MainUiEvent()
}
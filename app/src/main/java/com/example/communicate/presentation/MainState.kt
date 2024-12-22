package com.example.communicate.presentation

import com.example.communicate.domain.model.RandomString


sealed class MainState {
    data object Loading : MainState()
    data class SingleRandomString(val string: RandomString) : MainState()
    data class Error(val msg: String) : MainState()
}

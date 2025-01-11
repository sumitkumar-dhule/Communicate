package com.example.communicate.presentation

sealed class MainEvent {

    data class GetNewString(val length: String): MainEvent()
    data class Remove(val id: Int): MainEvent()
    data object ResetAll: MainEvent()
}
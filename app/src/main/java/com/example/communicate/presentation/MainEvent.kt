package com.example.communicate.presentation

sealed class MainEvent {

    data class GetNewString(val length: String): MainEvent()
    data class RemoveString(val id: Int): MainEvent()
    data object ResetAll: MainEvent()
}
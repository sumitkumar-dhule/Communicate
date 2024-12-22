package com.example.communicate.presentation


sealed class MainUiEvent {
    data class GetNewString(val length: String): MainUiEvent()
    data class RemoveString(val id: Int): MainUiEvent()
    data object ResetAll: MainUiEvent()
}
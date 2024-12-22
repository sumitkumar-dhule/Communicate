package com.example.communicate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.usecase.GetARandomStringUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getARandomStringUsecase: GetARandomStringUsecase) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow(MainState(RandomString(value = "", length = 0, created = "")))
    private val _uiEventChannel = Channel<MainUiEvent>(Channel.BUFFERED)

    val uiState = _uiState.asStateFlow()
    val uiEventFlow = _uiEventChannel.receiveAsFlow()

    fun sendUiEvent(uiEvent: MainUiEvent) {
        viewModelScope.launch {
            _uiEventChannel.send(uiEvent)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetNewString -> {
                getNewRandomString(event.length)
            }

            is MainEvent.RemoveString -> TODO()
            is MainEvent.ResetAll -> TODO()
        }
    }

    private fun getNewRandomString(length: String) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            try {
                val randString = async { getARandomStringUsecase(length.toInt()) }
                _uiState.update {
                    it.copy(
                        aRandomSting = randString.await(),
                        isLoading = false
                    )

                }
            } catch (exception: Exception) {

            }
        }
    }

}
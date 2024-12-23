package com.example.communicate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.usecase.GetARandomStringUsecase
import com.example.communicate.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _uiState = MutableStateFlow(MainState())
    private val _uiEventChannel = Channel<MainUiEvent>(Channel.BUFFERED)
    private val mutableList = mutableListOf<RandomString>()

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
            is MainEvent.ResetAll -> resetList()
        }
    }

    private fun resetList() {
        mutableList.clear()
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                stringList = mutableList.toList()
            )
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
                when (val result = getARandomStringUsecase(length.toInt())) {
                    is Result.Error -> {

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isError = true
                            )
                        }

                    }

                    is Result.Success -> {
                        mutableList.add(result.data)

                        _uiState.update {
                            it.copy(
                                stringList = mutableList.reversed().toList(),
                                isLoading = false,
                                isError = false,
                            )

                        }

                    }
                }


            } catch (exception: Exception) {

            }
        }
    }

}
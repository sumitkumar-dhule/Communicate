package com.example.communicate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communicate.domain.model.RandomString
import com.example.communicate.domain.usecase.ClearAllUsecase
import com.example.communicate.domain.usecase.GetARandomStringUsecase
import com.example.communicate.domain.usecase.GetAllRandomStringsUsecase
import com.example.communicate.domain.usecase.RemoveStringlUsecase
import com.example.communicate.presentation.util.isInvalidStringLength
import com.example.communicate.util.DataError
import com.example.communicate.util.Result
import com.example.communicate.util.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getARandomStringUsecase: GetARandomStringUsecase,
    private val getAllRandomStringsUsecase: GetAllRandomStringsUsecase,
    private val removeStringlUsecase: RemoveStringlUsecase,
    private val clearAllUsecase: ClearAllUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainState())
    private val _uiEventChannel = Channel<MainUiEvent>(Channel.BUFFERED)
    private var mutableList = mutableListOf<RandomString>()

    val uiState = _uiState.asStateFlow()
    val uiEventFlow = _uiEventChannel.receiveAsFlow()

    fun sendUiEvent(uiEvent: MainUiEvent) {
        viewModelScope.launch {
            _uiEventChannel.send(uiEvent)
        }
    }

    init {
        updateFromDb()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetNewString -> {
                loading()

                when (val lengthResult = isInvalidStringLength(event.length)) {
                    is Result.Error -> when (lengthResult.error) {
                        ValidationError.StringLength.ZERO ->
                            updateError("Please enter value greater than zero")

                        ValidationError.StringLength.NEGATIVE ->
                            updateError("Can not generate string with negative length")

                        ValidationError.StringLength.PARSEING ->
                            updateError("Enter valid string length")
                    }

                    is Result.Success -> {
                        getNewRandomString(lengthResult.data)
                    }
                }
            }

            is MainEvent.Remove -> remove(event.id)
            is MainEvent.ResetAll -> viewModelScope.launch { clearAllUsecase() }
        }
    }

    private fun remove(tag: Int) {
        viewModelScope.launch {
            when (val result = removeStringlUsecase(tag)) {
                is Result.Error -> updateError("Something went wrong. try again")
                is Result.Success -> Unit
            }
            updateFromDb()
        }
    }

    private fun getNewRandomString(length: Int) {
        viewModelScope.launch {
            try {
                withTimeout(5000) {
                    when (val result = getARandomStringUsecase(length)) {
                        is Result.Error -> {
                            when (result.error) {
                                DataError.Local.NO_PROVIDER ->
                                    updateError("No valid provider. Install provider app")

                                DataError.Local.INVALID_DATA ->
                                    updateError("Invalid data received. try again")

                                else ->
                                    updateError("Something went wrong. try again")
                            }

                        }

                        is Result.Success -> {
                            updateFromDb()
                        }
                    }
                }

            } catch (exception: TimeoutCancellationException) {
                updateError("Taking too long...timeout. Try again")
            } catch (exception: Exception) {
                updateError("Something went wrong. try again")
            }
        }
    }

    private fun updateFromDb() {
        viewModelScope.launch {
            try {

                when (val result = getAllRandomStringsUsecase()) {
                    is Result.Error -> {
                        updateError("Something went wrong. try again")
                    }

                    is Result.Success -> {
                        mutableList = result.data.toMutableList()
                        updateList(mutableList.toList())
                    }

                }
            } catch (exception: TimeoutCancellationException) {
                updateError("Taking too long...timeout. Try again")
            } catch (exception: Exception) {
                updateError("Something went wrong. try again")
            }
        }
    }


    private fun loading() {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    private fun updateList(list: List<RandomString>) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                stringList = list
            )
        }
    }

    private fun updateError(errorMessage: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
                stringList = mutableList.toList(),
                errorMessage = errorMessage
            )
        }
    }
}

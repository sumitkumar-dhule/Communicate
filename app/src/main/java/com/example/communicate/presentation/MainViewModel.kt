package com.example.communicate.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.communicate.domain.usecase.GetARandomStringUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getARandomStringUsecase: GetARandomStringUsecase) :
    ViewModel() {

    var state = mutableStateOf<MainState>(MainState.Loading)
        private set

    init {
        fromRepo()
    }

    private fun fromRepo() {
        viewModelScope.launch {
            state.value = MainState.Loading
            try {
                state.value = MainState.SingleRandomString(getARandomStringUsecase())
            } catch (exception: Exception) {
                state.value = MainState.Error(exception.localizedMessage)
            }
        }
    }
}
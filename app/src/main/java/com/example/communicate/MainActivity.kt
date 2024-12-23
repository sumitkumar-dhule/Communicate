package com.example.communicate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.communicate.presentation.MainScreen
import com.example.communicate.presentation.MainViewModel
import com.example.communicate.ui.theme.CommunicateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CommunicateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(Modifier.padding(innerPadding)) {

                        val mainViewModel: MainViewModel by viewModels()
                        val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

                        MainScreen(uiState = uiState,
                            onEvent = mainViewModel::onEvent,
                            uiEventChannel = mainViewModel.uiEventFlow
                        )
                    }
                }
            }
        }
    }


}

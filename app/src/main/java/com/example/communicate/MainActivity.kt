package com.example.communicate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.communicate.presentation.MainScreen
import com.example.communicate.presentation.MainViewModel
import com.example.communicate.ui.theme.CommunicateTheme
import com.example.communicate.util.getStringFromProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CommunicateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(Modifier.padding(innerPadding)) {
                        MainScreen(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(),
                            viewModel = mainViewModel
                        )
//
//                        Text(
//                            modifier = Modifier.padding(innerPadding),
//                            text = getStringFromProvider(context = this@MainActivity, length = 11)
//                        )
                    }
                }
            }
        }
    }


}

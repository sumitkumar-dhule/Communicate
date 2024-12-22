package com.example.communicate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.communicate.domain.model.RandomString

@Composable
fun MainScreen(modifier: Modifier, viewModel: MainViewModel) {

    Column(modifier = Modifier.fillMaxWidth()) {
        RequestData(modifier)
        when (val state = viewModel.state.value) {
            is MainState.Error -> TODO()
            is MainState.Loading -> TODO()
            is MainState.SingleRandomString -> RandomStringItem(state.string)
        }

        History()
    }

}

@Composable
fun RandomStringItem(randomString: RandomString) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        Text(text = randomString.value, modifier = Modifier.background(Color.LightGray))
        Text(text = randomString.length.toString(), modifier = Modifier.background(Color.LightGray))
        Text(text = randomString.created, modifier = Modifier.background(Color.LightGray))
    }
}

@Composable
fun RequestData(modifier: Modifier) {
    val text = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("String length") }
        )

        Button(modifier = Modifier.padding(8.dp), onClick = {}) {
            Text("Get New String")
        }

    }
}


@Composable
fun History() {

}

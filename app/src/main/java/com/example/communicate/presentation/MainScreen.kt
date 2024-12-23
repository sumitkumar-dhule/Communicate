package com.example.communicate.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.communicate.domain.model.RandomString
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    uiState: MainState,
    onEvent: (MainEvent) -> Unit,
    uiEventChannel: Flow<MainUiEvent>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        RequestData(onEvent)

        //TODO: Fix loading
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (uiState.isError) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Something went wrong")
            }
        } else {
            StringItemList(uiState.stringList)

        }
    }
}

@Composable
fun StringItemList(randomStrings: List<RandomString>) {
    LazyColumn {
        items(items = randomStrings) {
            RandomStringItem(randomString = it)
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                color = Color.LightGray
            )
        }
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
        Text(text = randomString.value, modifier = Modifier.weight(1.0f))
        VerticalDivider(modifier = Modifier.height(16.dp))
        Text(
            text = randomString.length.toString(),
            modifier = Modifier
                .width(30.dp)
                .padding(horizontal = 4.dp),
            textAlign = TextAlign.Center
        )
        VerticalDivider(modifier = Modifier.height(16.dp))
        Text(
            text = randomString.created, modifier = Modifier
                .width(200.dp)
                .padding(4.dp),
            textAlign = TextAlign.End

        )
    }
}

@Composable
fun RequestData(onEvent: (MainEvent) -> Unit) {
    val text = remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("String length") }
        )

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { onEvent(MainEvent.GetNewString(text.value)) }) {
            Text("Get New String")
        }
    }
}


package com.example.communicate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.communicate.R
import com.example.communicate.domain.model.RandomString
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    uiState: MainState,
    onEvent: (MainEvent) -> Unit,
    uiEventChannel: Flow<MainUiEvent>
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        RequestDataSection(onEvent)

        if (uiState.isError) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = uiState.errorMessage,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (uiState.stringList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Click on 'Get new String' to add data")
                }
            } else {
                StringItemList(uiState.stringList, onEvent)

            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
            color = Color.LightGray
        )

        ClearDataSection(onEvent)
    }
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun StringItemList(randomStrings: List<RandomString>, onEvent: (MainEvent) -> Unit) {
    LazyColumn {
        items(items = randomStrings) {
            RandomStringItem(randomString = it, onEvent)
            HorizontalDivider(
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun RandomStringItem(randomString: RandomString, onEvent: (MainEvent) -> Unit) {
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

        IconButton(
            modifier = Modifier.size(60.dp),
            onClick = { onEvent(MainEvent.Remove(randomString.id)) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_delete_outline),
                contentDescription = null,
                tint = Color.Red
            )
        }

    }
}

@Composable
fun RequestDataSection(onEvent: (MainEvent) -> Unit) {
    val text = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly
    ) {

        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text("String length") }
        )

        Button(
            onClick = { onEvent(MainEvent.GetNewString(text.value)) }) {
            Text("Get New String")
        }
    }
}

@Composable
fun ClearDataSection(onEvent: (MainEvent) -> Unit) {
    Button(
        modifier = Modifier.padding(32.dp),
        onClick = { onEvent(MainEvent.ResetAll) }) {
        Text("Clear All")
    }
}



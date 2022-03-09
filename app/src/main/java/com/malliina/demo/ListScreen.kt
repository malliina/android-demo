package com.malliina.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ListScreen(vm: DemoViewModel) {
  val items = vm.itemsFlow.collectAsState(initial = emptyList())
  MessagesList(items.value)
}

@Composable
fun MessagesList(messages: List<Message>) {
  // rememberSaveable survives orientation changes
  var text by rememberSaveable { mutableStateOf("") }
  Column {
    LazyColumn(Modifier.weight(1f)) {
      items(messages) { message ->
        MessageCard(message, big = true) {
        }
      }
    }
    TextField(
      value = text,
      onValueChange = { text = it },
      label = { Text("Write") },
      placeholder = { Text("Text goes here...") },
      modifier = Modifier.align(Alignment.End)
    )
  }
}

@Preview
@Composable
fun ListScreenPreview() {
  MessagesList(SampleData.messages.take(5))
}

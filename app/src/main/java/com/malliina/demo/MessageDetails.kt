package com.malliina.demo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.lang.Exception

sealed class Res<out T> {
  object Loading: Res<Nothing>()
  class Error(val error: Exception): Res<Nothing>()
  class Success<T>(val t: T): Res<T>()
}

@Composable
fun MessageDetails(
  navController: NavHostController,
  vm: DemoViewModel,
  messageId: String,
  onClick: () -> Unit
) {
  var res: Res<Message> by remember { mutableStateOf(Res.Loading) }
  LaunchedEffect(messageId) {
    delay(500)
    res = Res.Success(vm.loadMessage(messageId.toInt()))
  }
  Column {
    Text("Selected message ID ${messageId}.", Modifier.clickable { onClick() })
    when (val r = res) {
      Res.Loading -> CircularProgressIndicator()
      is Res.Success -> MessageCard(r.t, big = true) {

      }
    }
  }
}

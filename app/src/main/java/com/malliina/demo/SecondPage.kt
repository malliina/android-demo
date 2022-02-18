package com.malliina.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.malliina.demo.ui.theme.DemoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

@Composable
fun SecondPage(nav: NavHostController, flow: Flow<String>) {
  val msgs = flow.collectAsState(initial = "Yeah")
  Column {
    Text(msgs.value)
    Button(onClick = { nav.popBackStack() }) {
      Text("Go back")
    }
  }
}

@Preview(showBackground = true, name = "Second Page")
@Composable
fun SecondPagePreview() {
  DemoAppTheme {
    SecondPage(nav = rememberNavController(), listOf("Hej").asFlow())
  }
}

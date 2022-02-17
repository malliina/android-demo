package com.malliina.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun SecondPage(nav: NavHostController) {
  Column {
    Text("Hi")
    Button(onClick = { nav.popBackStack() }) {
      Text("Go back")
    }
  }
}

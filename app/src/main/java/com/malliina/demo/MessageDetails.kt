package com.malliina.demo

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MessageDetails(navController: NavHostController, onClick: () -> Unit) {
  Text("Hmm", Modifier.clickable { onClick() })
}

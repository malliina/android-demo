package com.malliina.demo

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.malliina.demo.ui.theme.DemoAppTheme

@Composable
fun DemoApp(viewModel: DemoViewModel, navController: NavHostController = rememberNavController()) {
  DemoAppTheme {
    val currentStack = navController.currentBackStackEntryAsState()
    Scaffold(
      topBar = {
        if (currentStack.value?.destination?.route == Nav.Main) {
          TopAppBar(title = { Text("Demo app") })
        } else {
          TopAppBar(title = { Text("Details") },
            navigationIcon = {
              IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
              }
            })
        }
      },
      bottomBar = {
        BottomAppBar {
          Text("Bottom app bar here")
        }
      }) {
      NavHost(navController = navController, startDestination = Nav.Main) {
        composable(Nav.Main) { ConversationPage(viewModel.flow, navController) }
        composable(Nav.Message) { MessageDetails(navController) }
        composable(Nav.Second) { SecondPage(navController) }
      }
    }
  }
}

//@Preview(showBackground = true, name = "Default Mode")
//@Composable
//fun DefaultPreview() {
//  DemoAppTheme {
//    Conversation(SampleData.pager.flow)
//  }
//}
//
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false, name = "Dark Mode")
//@Composable
//fun DarkModePreview() {
//  DemoAppTheme {
//    Conversation(SampleData.pager.flow)
//  }
//}

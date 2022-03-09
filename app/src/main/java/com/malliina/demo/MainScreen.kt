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
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MainScreen(viewModel: DemoViewModel, lang: Lang = Lang.english, navController: NavHostController = rememberNavController()) {
  DemoAppTheme {
    val currentStack = navController.currentBackStackEntryAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
      scaffoldState = scaffoldState,
      topBar = {
        if (currentStack.value?.destination?.route == Nav.Main) {
          TopAppBar(title = { Text(lang.title) })
        } else {
          TopAppBar(title = { Text(lang.details) },
            navigationIcon = {
              IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = lang.goBack)
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
        composable(Nav.Main) {
          ConversationPage(viewModel, navController, lang.conversations) {
            scope.launch {
              scaffoldState.snackbarHostState.showSnackbar("An error occurred.", "OK")
            }
          }
        }
        composable(Nav.MessageTemplate) { backStackEntry ->
          MessageDetails(
            navController,
            viewModel,
            backStackEntry.arguments?.getString(Nav.MessageParam) ?: ""
          ) {
            Timber.i("Clicked")
            scope.launch {
              scaffoldState.snackbarHostState.showSnackbar("Snackbar message", "OK")
            }
          }
        }
      }
    }
  }
}

package com.malliina.demo

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.malliina.demo.ui.theme.DemoAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class Screen(val name: String, val icon: ImageVector) {
  object Main : Screen(Nav.Main, Icons.Filled.Face)
  object List : Screen(Nav.List, Icons.Filled.List)
}

@Composable
fun MainScreen(
  viewModel: DemoViewModel,
  lang: Lang = Lang.english,
  navController: NavHostController = rememberNavController()
) {
  DemoAppTheme {
    val currentStack = navController.currentBackStackEntryAsState()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val currentRoute = currentStack.value?.destination?.route
    Scaffold(
      scaffoldState = scaffoldState,
      topBar = {
        when (currentRoute) {
          Nav.Main -> {
            TopAppBar(title = { Text(lang.title) })
          }
          Nav.List -> {
            TopAppBar(title = { Text(lang.list) })
          }
          else -> {
            TopAppBar(title = { Text(if (currentRoute == Nav.List) lang.list else lang.details) },
              navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                  Icon(Icons.Filled.ArrowBack, contentDescription = lang.goBack)
                }
              })
          }
        }
      },
      bottomBar = {
        // https://developer.android.com/jetpack/compose/navigation
        BottomNavigation {
          listOf(Screen.Main, Screen.List).forEach { screen ->
            BottomNavigationItem(
              icon = { Icon(screen.icon, contentDescription = null) },
              selected = currentRoute == screen.name,
              onClick = {
                navController.navigate(screen.name) {
                  popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                  }
                  launchSingleTop = true
                  restoreState = true
                }
              })
          }
        }
      }) { innerPadding ->
      NavHost(
        navController = navController,
        startDestination = Nav.Main,
        Modifier.padding(innerPadding)
      ) {
        composable(Nav.Main) {
          ConversationPage(viewModel, navController, lang.conversations) {
            scope.launch {
              scaffoldState.snackbarHostState.showSnackbar("An error occurred.", "OK")
            }
          }
        }
        composable(Nav.List) {
          ListScreen(viewModel)
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

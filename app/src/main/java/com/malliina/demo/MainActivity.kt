package com.malliina.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import timber.log.Timber

class MainActivity : ComponentActivity() {
  private val viewModel: DemoViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else NoLogging()
    Timber.plant(tree)
    Timber.i("onCreate")
    setContent {
      MainScreen(viewModel)
    }
  }

  override fun onStart() {
    super.onStart()
    Timber.i("onStart")
  }

  override fun onStop() {
    super.onStop()
    Timber.i("onStop")
  }

  override fun onRestart() {
    super.onRestart()
    Timber.i("onRestart")
  }

  override fun onDestroy() {
    super.onDestroy()
    Timber.i("onDestroy")
  }
}


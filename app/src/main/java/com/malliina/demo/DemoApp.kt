package com.malliina.demo

import android.app.Application
import timber.log.Timber

class DemoApp: Application() {
  init {
    val tree = if (BuildConfig.DEBUG) Timber.DebugTree() else NoLogging()
    Timber.plant(tree)
  }
  val http: HttpClient = HttpClient()
}

package com.malliina.demo

import timber.log.Timber

class NoLogging : Timber.Tree() {
  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
  }
}

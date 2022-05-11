package com.malliina.demo

import android.os.Build

data class ConversationLang(val tap: String, val demo: String)

data class Lang(
  val title: String,
  val details: String,
  val list: String,
  val goBack: String,
  val conversations: ConversationLang
) {
  companion object {
    val version = BuildConfig.VERSION_NAME
    val english =
      Lang(
        "Demo app",
        "Details",
        "List",
        "Go back",
        ConversationLang("Tap or swipe to refresh $version", "Demo")
      )
  }
}

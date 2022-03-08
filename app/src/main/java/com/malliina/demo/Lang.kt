package com.malliina.demo

data class ConversationLang(val tap: String, val demo: String)

data class Lang(
  val title: String,
  val details: String,
  val goBack: String,
  val conversations: ConversationLang
) {
  companion object {
    val english =
      Lang("Demo app", "Details", "Go back", ConversationLang("Tap or swipe to refresh", "Demo"))
  }
}
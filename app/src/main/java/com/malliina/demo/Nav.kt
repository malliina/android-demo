package com.malliina.demo

object Nav {
  const val Main = "main"
  const val Second = "detail"
  const val Message = "message"
  const val MessageParam = "messageId"
  const val MessageTemplate = "$Message/{$MessageParam}"
  fun message(id: String) = "$Message/$id"
}

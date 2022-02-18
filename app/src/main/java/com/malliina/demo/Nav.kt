package com.malliina.demo

object Nav {
  val Main = "main"
  val Second = "detail"
  val Message = "message"
  val MessageParam = "messageId"
  val MessageTemplate = "$Message/{$MessageParam}"
  fun message(id: String) = "$Message/$id"
}

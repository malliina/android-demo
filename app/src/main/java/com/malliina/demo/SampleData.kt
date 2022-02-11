package com.malliina.demo

object SampleData {
  val messages = (1..100).flatMap { idx ->
    listOf(
      Message("Bob", "Hejsan, there $idx"),
      Message(
        "Alice",
        "Hey, Bob. This is a very long message, designed to span more than one line. $idx"
      ),
      Message("Bob", "Let's go for lunch $idx"),
    )
  }
}

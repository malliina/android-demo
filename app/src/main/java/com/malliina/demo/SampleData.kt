package com.malliina.demo

import androidx.paging.Pager
import androidx.paging.PagingConfig

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

  val pager: Pager<LimitOffset, Message> = Pager(
    PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)
  ) {
    DemoPagingSource()
  }
}

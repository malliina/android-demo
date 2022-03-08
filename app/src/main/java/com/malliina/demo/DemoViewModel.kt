package com.malliina.demo

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DemoViewModel(private val d: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
  private val pager: Pager<LimitOffset, Message> = Pager(
    PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)
  ) {
    DemoPagingSource()
  }

  //  val flow = pager.flow.cachedIn(viewModelScope)
  val flow = pager.flow

  suspend fun loadMessage(id: Int): Message = withContext(d) {
    SampleData.messages[id]
  }
}

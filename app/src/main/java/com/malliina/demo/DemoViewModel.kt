package com.malliina.demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Response
import timber.log.Timber

class DemoViewModel(appl: Application) : AndroidViewModel(appl) {
  private val app: DemoApp get() = getApplication()
  private val pager: Pager<LimitOffset, Message> = Pager(
    PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)
  ) {
    DemoPagingSource()
  }
  val flow = pager.flow
  private val url = "https://www.google.com".toHttpUrl()

  suspend fun items(): List<Message> = withContext(Dispatchers.IO) {
    SampleData.messages.take(5)
  }

  val itemsFlow: Flow<List<Message>> = flow {
    val latest = items()
    Timber.i("Got ${latest.size} items. Emitting...")
    emit(latest)
  }.flowOn(Dispatchers.IO)

  suspend fun loadMessage(id: Int): Message = withContext(Dispatchers.IO) {
    SampleData.messages[id]
  }

  suspend fun makeRequest(): Response = app.http.makeRequest(url)
}

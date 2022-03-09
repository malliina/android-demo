package com.malliina.demo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Response

class DemoViewModel(appl: Application) : AndroidViewModel(appl) {
  private val app: DemoApp get() = getApplication()
  private val pager: Pager<LimitOffset, Message> = Pager(
    PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)
  ) {
    DemoPagingSource()
  }
  val flow = pager.flow
  private val url = "https://www.google.com".toHttpUrl()

  suspend fun loadMessage(id: Int): Message = withContext(Dispatchers.IO) {
    SampleData.messages[id]
  }

  suspend fun makeRequest(): Response = app.http.makeRequest(url)
}

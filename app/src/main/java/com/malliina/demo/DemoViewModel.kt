package com.malliina.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class DemoViewModel: ViewModel() {
  val pager: Pager<LimitOffset, Message> = Pager(
    PagingConfig(pageSize = 20, enablePlaceholders = true, maxSize = 200)
  ) {
    DemoPagingSource()
  }
  val flow = pager.flow.cachedIn(viewModelScope)
}

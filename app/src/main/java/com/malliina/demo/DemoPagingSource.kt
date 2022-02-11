package com.malliina.demo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import timber.log.Timber
import kotlin.math.max

data class LimitOffset(val limit: Int, val offset: Int)

class DemoPagingSource : PagingSource<LimitOffset, Message>() {
  override fun getRefreshKey(state: PagingState<LimitOffset, Message>): LimitOffset? {
    val pageSize = state.config.pageSize
    val nextOffset = state.pages.maxOfOrNull { t -> (t.prevKey?.offset ?: 0) + pageSize } ?: 0
    Timber.i("Refresh key anchor is ${state.anchorPosition}, returning next offset as $nextOffset")
    return LimitOffset(pageSize, nextOffset)
  }

  override suspend fun load(params: LoadParams<LimitOffset>): LoadResult<LimitOffset, Message> {
    Timber.i("Loading ${params.key} load size ${params.loadSize}")
    val limits = params.key ?: LimitOffset(params.loadSize, 0)
    val items = SampleData.messages.drop(limits.offset).take(limits.limit)
    return LoadResult.Page(
      items,
      if (limits.offset > 0) LimitOffset(20, max(limits.offset - 20, 0)) else null,
      if (items.isEmpty()) null else LimitOffset(limits.limit, offset = limits.offset + 20)
    )
  }
}

package com.malliina.demo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.max

data class LimitOffset(val limit: Int, val offset: Int) {
  fun next() = LimitOffset(limit, offset + limit)
  fun prev() = LimitOffset(limit, offset - limit)
}

/**
 * @see https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data
 */
class DemoPagingSource : PagingSource<LimitOffset, Message>() {
  /** "the page key of the closest page to anchorPosition, from either the prevKey or the nextKey"
   */
  override fun getRefreshKey(state: PagingState<LimitOffset, Message>): LimitOffset? {
    return state.anchorPosition?.let { anchor ->
      val anchorPage = state.closestPageToPosition(anchor)
      anchorPage?.prevKey?.next() ?: anchorPage?.nextKey?.prev()
    }
  }

  override suspend fun load(params: LoadParams<LimitOffset>): LoadResult<LimitOffset, Message> {
    return withContext(Dispatchers.IO) {
      Timber.i("Loading ${params.key} load size ${params.loadSize}")
      val limits = params.key ?: LimitOffset(params.loadSize, 0)
      val items = SampleData.messages.drop(limits.offset).take(limits.limit)
      delay(1000)
      LoadResult.Page(
        items,
        if (limits.offset > 0) LimitOffset(limits.limit, max(limits.offset - limits.limit, 0)) else null,
        if (items.isEmpty()) null else LimitOffset(limits.limit, offset = limits.offset + limits.limit)
      )
//      if (limits.offset == 0) {
//        LoadResult.Page(
//          items,
//          if (limits.offset > 0) LimitOffset(limits.limit, max(limits.offset - limits.limit, 0)) else null,
//          if (items.isEmpty()) null else LimitOffset(limits.limit, offset = limits.offset + limits.limit)
//        )
//      } else {
//        LoadResult.Error(Exception("Demo network error."))
//      }
    }
  }
}

package com.fphoenixcorneae.eyepetizer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.https.mainService
import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply

/**
 * @desc：
 * @date：2023/08/11 16:21
 */
class HomepageDailyPagingSource : PagingSource<String, HomepageReply.Item>() {

    override fun getRefreshKey(state: PagingState<String, HomepageReply.Item>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, HomepageReply.Item> =
        runCatching {
            val url = params.key ?: EyepetizerApi.HOMEPAGE_DAILY
            val repoResponse = mainService.getHomepageData(url = url)
            val repoItems = repoResponse?.itemList.orEmpty()
            val prevKey = null
            val nextKey = if (repoItems.isNotEmpty() && !repoResponse?.nextPageUrl.isNullOrEmpty()) {
                repoResponse?.nextPageUrl
            } else {
                null
            }
            LoadResult.Page(data = repoItems, prevKey = prevKey, nextKey = nextKey)
        }.onFailure {
            it.printStackTrace()
        }.getOrElse {
            LoadResult.Error(it)
        }
}
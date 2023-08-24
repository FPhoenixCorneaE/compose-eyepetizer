package com.fphoenixcorneae.eyepetizer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.https.mainService
import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply

/**
 * @desc：
 * @date：2023/08/24 10:59
 */
class CommunityFollowPagingSource : PagingSource<String, CommunityReply.Item>() {

    override fun getRefreshKey(state: PagingState<String, CommunityReply.Item>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, CommunityReply.Item> =
        runCatching {
            val url = params.key ?: EyepetizerApi.COMMUNITY_FOLLOW
            val repoResponse = mainService.getCommunityData(url = url)
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
package com.fphoenixcorneae.eyepetizer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.https.mainService
import com.fphoenixcorneae.eyepetizer.mvi.model.PushMessageReply

/**
 * @desc：
 * @date：2023/08/24 17:40
 */
class NotificationPushMessagePagingSource : PagingSource<String, PushMessageReply.Message>() {

    override fun getRefreshKey(state: PagingState<String, PushMessageReply.Message>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PushMessageReply.Message> =
        runCatching {
            val url = params.key ?: EyepetizerApi.NOTIFICATION_PUSH_MESSAGE
            val repoResponse = mainService.getNotificationPushMessage(url = url)
            val repoItems = repoResponse?.messageList.orEmpty()
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
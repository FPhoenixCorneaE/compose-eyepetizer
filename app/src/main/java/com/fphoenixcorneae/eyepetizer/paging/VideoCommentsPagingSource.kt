package com.fphoenixcorneae.eyepetizer.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.https.videoService
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoCommentsReply
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.VideoDetailViewModel

/**
 * @desc：
 * @date：2023/08/17 17:25
 */
class VideoCommentsPagingSource(
    private val viewModel: VideoDetailViewModel,
) : PagingSource<String, VideoCommentsReply.Item>() {

    override fun getRefreshKey(state: PagingState<String, VideoCommentsReply.Item>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, VideoCommentsReply.Item> =
        runCatching {
            val url = params.key ?: "${EyepetizerApi.VIDEO_DETAIL_COMMENTS}${viewModel.getVideoId()}"
            val repoResponse = videoService.getVideoComments(url = url)
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
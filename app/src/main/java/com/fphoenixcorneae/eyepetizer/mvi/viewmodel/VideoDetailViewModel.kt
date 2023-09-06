package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fphoenixcorneae.compose.ext.launchDefault
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.https.videoService
import com.fphoenixcorneae.eyepetizer.mvi.model.HomepageReply
import com.fphoenixcorneae.eyepetizer.mvi.model.VideoDetailReply
import com.fphoenixcorneae.eyepetizer.paging.VideoCommentsPagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @desc：
 * @date：2023/08/17 17:03
 */
class VideoDetailViewModelFactory(private val videoId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoDetailViewModel::class.java)) {
            return VideoDetailViewModel(videoId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * @desc：
 * @date：2023/08/17 16:46
 */
class VideoDetailViewModel(
    private var videoId: String,
) : BaseViewModel<VideoDetailAction>() {

    /** 评论列表 */
    val videoComments = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        VideoCommentsPagingSource(viewModel = this)
    }.flow.cachedIn(viewModelScope)

    private val _videoDetailUiState = MutableStateFlow(VideoDetailUiState(videoId = videoId))
    val videoDetailUiState = _videoDetailUiState.asStateFlow()

    fun getVideoId() = videoId

    private fun getVideoDetail(videoId: String) {
        sendHttpRequest(
            call = {
                videoService.getVideoDetail(videoId = videoId)
            },
        ) { reply ->
            _videoDetailUiState.update {
                it.copy(videoDetailReply = reply)
            }
        }
    }

    private fun getVideoRelated(videoId: String) {
        sendHttpRequest(
            call = {
                videoService.getVideoRelated(videoId = videoId)
            },
        ) { reply ->
            _videoDetailUiState.update {
                it.copy(videoRelatedReply = reply)
            }
        }
    }

    private fun updateVideoId(videoId: String) {
        launchDefault {
            this.videoId = videoId
            _videoDetailUiState.update {
                it.copy(videoId = videoId, videoDetailReply = null, videoRelatedReply = null)
            }
        }
    }

    override fun processIntent(action: VideoDetailAction) {
        when (action) {
            VideoDetailAction.Initialize -> {
                getVideoDetail(videoId = videoId)
                getVideoRelated(videoId = videoId)
            }

            is VideoDetailAction.Refresh -> {
                updateVideoId(videoId = action.videoId)
                getVideoDetail(videoId = action.videoId)
                getVideoRelated(videoId = action.videoId)
            }
        }
    }
}

/**
 * @desc：
 * @date：2023/08/17 17:30
 */
data class VideoDetailUiState(
    val videoId: String = "",
    val videoDetailReply: VideoDetailReply? = null,
    val videoRelatedReply: HomepageReply? = null,
)

/**
 * @desc：
 * @date：2023/09/06 11:10
 */
sealed interface VideoDetailAction {
    object Initialize : VideoDetailAction
    data class Refresh(val videoId: String) : VideoDetailAction
}
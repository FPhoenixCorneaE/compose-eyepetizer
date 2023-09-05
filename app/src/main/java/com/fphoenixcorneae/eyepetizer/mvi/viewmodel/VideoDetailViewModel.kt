package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fphoenixcorneae.compose.ext.launchDefault
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.compose.mvi.DefaultAction
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
) : BaseViewModel<DefaultAction>() {

    /** 评论列表 */
    val videoComments = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        VideoCommentsPagingSource(videoId = videoId)
    }.flow.cachedIn(viewModelScope)

    private val _videoDetailUiState = MutableStateFlow(VideoDetailUiState())
    val videoDetailUiState = _videoDetailUiState.asStateFlow()

    fun updateVideoId(videoId: String) {
        launchDefault {
            this.videoId = videoId
            _videoDetailUiState.update {
                it.copy(videoDetailReply = null, videoRelatedReply = null)
            }
        }
    }

    override fun processIntent(action: DefaultAction) {
        when (action) {
            DefaultAction.Initialize -> {
                sendHttpRequest(
                    call = {
                        videoService.getVideoDetail(videoId = videoId)
                    },
                ) { reply ->
                    _videoDetailUiState.update {
                        it.copy(videoDetailReply = reply)
                    }
                }
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

            else -> {}
        }
    }
}

/**
 * @desc：
 * @date：2023/08/17 17:30
 */
data class VideoDetailUiState(
    val videoDetailReply: VideoDetailReply? = null,
    val videoRelatedReply: HomepageReply? = null,
)
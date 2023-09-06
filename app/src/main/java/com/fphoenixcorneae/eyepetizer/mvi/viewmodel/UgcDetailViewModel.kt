package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import com.fphoenixcorneae.compose.ext.launch
import com.fphoenixcorneae.compose.ext.toObject
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.mvi.model.CommunityReply
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * @desc：
 * @date：2023/08/29 14:44
 */
class UgcDetailViewModel : BaseViewModel<UgcDetailAction>() {

    /** 社区推荐列表 */
    val communityCommends by lazy {
        MutableStateFlow(
            getCommunityCommendsFromLocal()
                .also {
                    if (it.isNullOrEmpty()) {
                        showError(Throwable(message = "数据还没有准备好，请重新尝试！"))
                    }
                }
        )
    }

    private fun getCommunityCommendsFromLocal() = MMKV.defaultMMKV().decodeString(Constant.Key.COMMUNITY_COMMEND_LIST)
        ?.toObject<List<CommunityReply.Item>>(object : TypeToken<List<CommunityReply.Item>>() {}.type)

    private val _ugcDetailUiState = MutableStateFlow(UgcDetailUiState())
    val ugcDetailUiState = _ugcDetailUiState.asStateFlow()

    private fun toggleBackAndUgcInfoVisibility() {
        launch {
            _ugcDetailUiState.update {
                it.copy(visibleBackAndUgcInfo = !it.visibleBackAndUgcInfo)
            }
        }
    }

    override fun processIntent(action: UgcDetailAction) {
        when (action) {
            UgcDetailAction.Refresh -> launch {
                getCommunityCommendsFromLocal().let {
                    if (it.isNullOrEmpty()) {
                        showError(Throwable(message = "数据还没有准备好，请重新尝试！"))
                    } else {
                        communityCommends.emit(getCommunityCommendsFromLocal())
                        showContent(it)
                    }
                }
            }

            UgcDetailAction.ToggleBackAndUgcInfoVisibility -> toggleBackAndUgcInfoVisibility()
        }
    }
}

/**
 * @desc：
 * @date：2023/08/31 11:00
 */
data class UgcDetailUiState(
    val visibleBackAndUgcInfo: Boolean = true,
)

/**
 * @desc：
 * @date：2023/09/06 11:08
 */
sealed interface UgcDetailAction {
    object Refresh : UgcDetailAction
    object ToggleBackAndUgcInfoVisibility : UgcDetailAction
}
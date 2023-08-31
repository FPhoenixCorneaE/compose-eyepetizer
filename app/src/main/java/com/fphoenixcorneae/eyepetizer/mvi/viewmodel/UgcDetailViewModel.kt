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
            MMKV.defaultMMKV().decodeString(Constant.Key.COMMUNITY_COMMEND_LIST)
                ?.toObject<List<CommunityReply.Item>>(object : TypeToken<List<CommunityReply.Item>>() {}.type)
        )
    }

    private val _ugcDetailUiState = MutableStateFlow(UgcDetailUiState())
    val ugcDetailUiState = _ugcDetailUiState.asStateFlow()

    fun toggleBackAndUgcInfoVisibility() {
        launch {
            _ugcDetailUiState.update {
                it.copy(visibleBackAndUgcInfo = !it.visibleBackAndUgcInfo)
            }
        }
    }

    override fun processIntent(action: UgcDetailAction) {
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
 * @date：2023/08/29 14:44
 */
sealed interface UgcDetailAction
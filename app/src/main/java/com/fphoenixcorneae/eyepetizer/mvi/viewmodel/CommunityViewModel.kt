package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.paging.CommunityCommendPagingSource
import com.fphoenixcorneae.eyepetizer.paging.CommunityFollowPagingSource

/**
 * @desc：
 * @date：2023/08/23 14:23
 */
class CommunityViewModel : BaseViewModel<CommunityAction>() {

    /** 社区推荐列表 */
    val communityCommends = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        CommunityCommendPagingSource()
    }.flow.cachedIn(viewModelScope)

    /** 社区关注列表 */
    val communityFollows = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        CommunityFollowPagingSource()
    }.flow.cachedIn(viewModelScope)

    override fun processIntent(action: CommunityAction) {
    }
}

/**
 * @desc：
 * @date：2023/08/23 14:23
 */
sealed interface CommunityAction
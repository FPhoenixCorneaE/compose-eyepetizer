package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.paging.NotificationPushMessagePagingSource

/**
 * @desc：
 * @date：2023/08/24 17:40
 */
class NotificationViewModel:BaseViewModel<NotificationAction>() {
    /** 通知推送列表 */
    val pushMessages = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        NotificationPushMessagePagingSource()
    }.flow.cachedIn(viewModelScope)

    override fun processIntent(action: NotificationAction) {
    }
}

/**
 * @desc：
 * @date：2023/08/24 17:40
 */
sealed interface NotificationAction
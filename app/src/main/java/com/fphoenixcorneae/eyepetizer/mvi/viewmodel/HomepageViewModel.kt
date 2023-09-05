package com.fphoenixcorneae.eyepetizer.mvi.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fphoenixcorneae.compose.mvi.BaseViewModel
import com.fphoenixcorneae.compose.mvi.DefaultAction
import com.fphoenixcorneae.eyepetizer.https.EyepetizerApi
import com.fphoenixcorneae.eyepetizer.paging.HomepageCommendPagingSource
import com.fphoenixcorneae.eyepetizer.paging.HomepageDailyPagingSource
import com.fphoenixcorneae.eyepetizer.paging.HomepageDiscoveryPagingSource

/**
 * @desc：
 * @date：2023/08/10 09:27
 */
class HomepageViewModel : BaseViewModel<DefaultAction>() {

    /** 首页发现列表 */
    val homepageDiscoveries = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        HomepageDiscoveryPagingSource()
    }.flow.cachedIn(viewModelScope)

    /** 首页推荐列表 */
    val homepageCommends = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        HomepageCommendPagingSource()
    }.flow.cachedIn(viewModelScope)

    /** 首页日报列表 */
    val homepageDailies = Pager(config = PagingConfig(pageSize = EyepetizerApi.PAGE_SIZE)) {
        HomepageDailyPagingSource()
    }.flow.cachedIn(viewModelScope)

    override fun processIntent(action: DefaultAction) {
    }
}
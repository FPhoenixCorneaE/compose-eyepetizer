package com.fphoenixcorneae.eyepetizer.mvi.ui.page.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefresh
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.HomepageViewModel

/**
 * @desc：首页-发现
 * @date：2023/08/11 16:52
 */
@Composable
fun DiscoveryScreen() {
    val viewModel = viewModel<HomepageViewModel>()
    val homepageDiscoveries = viewModel.homepageDiscoveries.collectAsLazyPagingItems()
    SwipeRefresh(lazyPagingItems = homepageDiscoveries) {
        items(homepageDiscoveries.itemCount) {
            val item = homepageDiscoveries[it] ?: return@items
            HomepageItem(item = item, position = it, playTag = Constant.PlayTag.DISCOVERY)
        }
    }
}
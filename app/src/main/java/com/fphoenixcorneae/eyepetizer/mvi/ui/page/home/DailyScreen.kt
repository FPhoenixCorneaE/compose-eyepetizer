package com.fphoenixcorneae.eyepetizer.mvi.ui.page.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.fphoenixcorneae.eyepetizer.const.Constant
import com.fphoenixcorneae.eyepetizer.mvi.ui.widget.SwipeRefresh
import com.fphoenixcorneae.eyepetizer.mvi.viewmodel.HomepageViewModel

/**
 * @desc：首页-日报
 * @date：2023/08/11 16:52
 */
@Composable
fun DailyScreen(){
    val viewModel = viewModel<HomepageViewModel>()
    val homepageDailies = viewModel.homepageDailies.collectAsLazyPagingItems()
    SwipeRefresh(lazyPagingItems = homepageDailies) {
        items(homepageDailies.itemCount) {
            val item = homepageDailies[it] ?: return@items
            HomepageItem(item = item, position = it, playTag = Constant.PlayTag.DAILY)
        }
    }
}
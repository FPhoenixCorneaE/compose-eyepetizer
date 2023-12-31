package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.fphoenixcorneae.eyepetizer.R
import com.fphoenixcorneae.eyepetizer.ext.clickableNoRipple
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Black70
import com.fphoenixcorneae.eyepetizer.mvi.ui.theme.Gray30

/**
 * 瀑布流下拉加载封装
 * implementation "com.google.accompanist:accompanist-swiperefresh:xxx"
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun <T : Any> SwipeRefreshStaggeredGrid(
    lazyPagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    lazyListState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    columnsCount: Int = 2,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalItemSpacing: Dp = 8.dp,
    horizontalItemSpacing: Dp = 8.dp,
    content: LazyStaggeredGridScope.() -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { lazyPagingItems.refresh() },
    )
    Box(
        modifier = Modifier
            .then(modifier)
            .pullRefresh(state = pullRefreshState)
    ) {
        PagingLazyColumn(
            lazyPagingItems = lazyPagingItems,
            lazyListState = lazyListState,
            contentPadding = contentPadding,
            columnsCount = columnsCount,
            verticalItemSpacing = verticalItemSpacing,
            horizontalItemSpacing = horizontalItemSpacing,
            content = content,
        )
        PullRefreshIndicator(
            refreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <T : Any> PagingLazyColumn(
    lazyPagingItems: LazyPagingItems<T>,
    lazyListState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    columnsCount: Int = 2,
    verticalItemSpacing: Dp = 8.dp,
    horizontalItemSpacing: Dp = 8.dp,
    content: LazyStaggeredGridScope.() -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(count = columnsCount),
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        verticalItemSpacing = verticalItemSpacing,
        horizontalArrangement = Arrangement.spacedBy(space = horizontalItemSpacing),
        contentPadding = contentPadding,
    ) {
        content()
        lazyColumnFooter(lazyPagingItems)
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyStaggeredGridScope.lazyColumnFooter(lazyPagingItems: LazyPagingItems<T>) {
    lazyPagingItems.apply {
        when {
            loadState.append is LoadState.Loading -> {
                // 加载更多，底部loading
                item(span = StaggeredGridItemSpan.FullLine) { LoadingItem() }
            }

            loadState.append is LoadState.Error -> {
                // 加载更多异常
                item(span = StaggeredGridItemSpan.FullLine) {
                    ErrorMoreRetryItem {
                        lazyPagingItems.retry()
                    }
                }
            }

            loadState.append == LoadState.NotLoading(endOfPaginationReached = true) -> {
                // 没有更多数据了
                item(span = StaggeredGridItemSpan.FullLine) { NoMoreDataItem() }
            }

            loadState.refresh is LoadState.Error -> {
                if (lazyPagingItems.itemCount <= 0) {
                    // 刷新的时候，如果itemCount小于0，第一次加载异常
                    item(span = StaggeredGridItemSpan.FullLine) {
                        ErrorContent {
                            lazyPagingItems.retry()
                        }
                    }
                } else {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        ErrorMoreRetryItem {
                            lazyPagingItems.retry()
                        }
                    }
                }
            }

            loadState.refresh is LoadState.Loading -> {
                // 第一次加载且正在加载中
                if (lazyPagingItems.itemCount == 0) {
                }
            }
        }
    }
}

/**
 * 底部加载更多失败处理
 */
@Composable
private fun ErrorMoreRetryItem(retry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickableNoRipple { retry() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "加载更多失败，请重试！",
            color = Black70,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(8.dp),
        )
    }
}

/**
 * 底部加载更多到底了
 */
@Composable
private fun NoMoreDataItem() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        val context = LocalContext.current
        Text(
            text = "- The End -",
            color = Black70,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp),
            fontFamily = ResourcesCompat.getFont(context, R.font.lobster_1_4)
                ?.let { FontFamily(it) },
        )
    }
}

/**
 * 页面加载失败处理
 */
@Composable
private fun ErrorContent(retry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(top = 80.dp),
            painter = painterResource(id = R.mipmap.ic_load_error),
            contentDescription = null
        )
        Text(text = "请求失败，请检查网络！", modifier = Modifier.padding(8.dp))
        TextButton(
            onClick = { retry() },
            modifier = Modifier
                .padding(20.dp)
                .width(80.dp)
                .height(30.dp),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(5.dp),
            colors = textButtonColors(backgroundColor = Gray30, contentColor = Black70),
            elevation = elevation(
                defaultElevation = 2.dp,
                pressedElevation = 4.dp,
            )
        ) { Text(text = "重试", color = Black70, fontSize = 14.sp) }
    }
}

/**
 * 底部加载更多正在加载中...
 */
@Composable
private fun LoadingItem() {
    Row(
        modifier = Modifier
            .height(34.dp)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp),
            color = Black70,
            strokeWidth = 2.dp
        )
        Text(
            text = "加载中...",
            color = Black70,
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            fontSize = 14.sp,
        )
    }
}